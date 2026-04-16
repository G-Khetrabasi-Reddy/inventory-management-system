package com.inventory.service.impl;

import com.inventory.dto.StockMovementDTO;
import com.inventory.entity.*;
import com.inventory.enums.MovementType;
import com.inventory.event.StockChangedEvent;
import com.inventory.exceptions.ProductNotFoundException;
import com.inventory.exceptions.UserNotFoundException;
import com.inventory.exceptions.WarehouseNotFoundException;
import com.inventory.mapper.StockMovementMapper;
import com.inventory.repository.*;
import com.inventory.service.StockMovementService;
import com.inventory.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockMovementServiceImpl implements StockMovementService {

    private final StockMovementRepository movementRepo;
    private final ProductRepository productRepo;
    private final WarehouseRepository warehouseRepo;
    private final UserRepository userRepo;

    // From Module 3 — all stock mutations go through StockService
    private final StockService stockService;

    // CRITICAL-02 FIX: Use Spring event publisher instead of CompletableFuture.runAsync()
    private final ApplicationEventPublisher eventPublisher;

    // ═══════════════════════════════════════════════════════════════════
    //  🔥 CORE METHOD — All stock changes MUST route through here
    // ═══════════════════════════════════════════════════════════════════

    @Override
    @Transactional  // CRITICAL-01 FIX: ensures atomicity across all stock operations
    public StockMovementDTO recordMovement(StockMovementDTO dto) {

        Product product = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Warehouse warehouse = warehouseRepo.findById(dto.getWarehouseId())
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found"));

        User user = userRepo.findById(dto.getPerformedBy())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Ensure stock record exists before mutating
        stockService.createStockIfNotExists(product.getProductId(), warehouse.getWarehouseId());

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setWarehouse(warehouse);
        movement.setMovementType(dto.getMovementType());
        movement.setQuantity(dto.getQuantity());
        movement.setReferenceType(dto.getReferenceType());
        movement.setReferenceId(dto.getReferenceId());
        movement.setPerformedBy(user);

        switch (dto.getMovementType()) {

            case IN -> {
                stockService.addStock(product.getProductId(),
                        warehouse.getWarehouseId(),
                        dto.getQuantity());
            }

            case OUT -> {
                stockService.reduceAvailableStock(product.getProductId(),
                        warehouse.getWarehouseId(),
                        dto.getQuantity());
            }

            case TRANSFER -> {

                if (dto.getDestinationWarehouseId() == null) {
                    throw new WarehouseNotFoundException("Destination warehouse required for TRANSFER");
                }

                Warehouse dest = warehouseRepo.findById(dto.getDestinationWarehouseId())
                        .orElseThrow(() -> new WarehouseNotFoundException("Destination warehouse not found"));

                movement.setDestinationWarehouse(dest);

                // Source ↓  reduce
                stockService.reduceAvailableStock(product.getProductId(),
                        warehouse.getWarehouseId(),
                        dto.getQuantity());

                // Destination ↑  add
                stockService.createStockIfNotExists(product.getProductId(), dest.getWarehouseId());

                stockService.addStock(product.getProductId(),
                        dest.getWarehouseId(),
                        dto.getQuantity());
            }

            // CRITICAL-04 FIX: available → reserved (sales reservation)
            case RESERVE -> {
                stockService.reserveStock(product.getProductId(),
                        warehouse.getWarehouseId(),
                        dto.getQuantity());
            }

            // CRITICAL-04 FIX: reserved → deducted (sales delivery/dispatch)
            case DISPATCH -> {
                stockService.deductStock(product.getProductId(),
                        warehouse.getWarehouseId(),
                        dto.getQuantity());
            }

            // CRITICAL-05 FIX: tracks expected incoming stock (purchase approval)
            case ON_ORDER -> {
                stockService.addQuantityOnOrder(product.getProductId(),
                        warehouse.getWarehouseId(),
                        dto.getQuantity());
            }
        }

        // Persist the movement record
        StockMovement savedMovement = movementRepo.save(movement);

        // ───────────────────────────────────────────────────────────────
        //  CRITICAL-02 FIX: Publish a domain event instead of using
        //  CompletableFuture.runAsync(). The StockAlertListener fires
        //  ONLY after this transaction commits (AFTER_COMMIT phase),
        //  preventing:
        //    1. Stale / uncommitted data reads
        //    2. Phantom alerts from rolled-back transactions
        //    3. Silent error swallowing
        // ───────────────────────────────────────────────────────────────
        eventPublisher.publishEvent(new StockChangedEvent(
                this,
                dto.getProductId(),
                dto.getWarehouseId(),
                dto.getDestinationWarehouseId(),
                dto.getMovementType()
        ));

        return StockMovementMapper.toDTO(savedMovement);
    }


    // ═══════════════════════════════════════════════════════
    //  Domain-specific convenience methods
    // ═══════════════════════════════════════════════════════

    // 📥 PURCHASE
    @Override
    @Transactional
    public StockMovementDTO handlePurchase(StockMovementDTO dto) {
        dto.setMovementType(MovementType.IN);
        return recordMovement(dto);
    }

    // 📤 SALE
    @Override
    @Transactional
    public StockMovementDTO handleSale(StockMovementDTO dto) {
        dto.setMovementType(MovementType.OUT);
        return recordMovement(dto);
    }

    // 🔄 TRANSFER
    @Override
    @Transactional
    public StockMovementDTO handleTransfer(StockMovementDTO dto) {
        dto.setMovementType(MovementType.TRANSFER);
        return recordMovement(dto);
    }

    // 💥 DAMAGE
    @Override
    @Transactional
    public StockMovementDTO handleDamage(StockMovementDTO dto) {
        dto.setMovementType(MovementType.OUT);
        return recordMovement(dto);
    }

    // ⚖️ ADJUSTMENT
    @Override
    @Transactional
    public StockMovementDTO handleAdjustment(StockMovementDTO dto) {

        if (dto.getQuantity() > 0) {
            dto.setMovementType(MovementType.IN);
        } else {
            dto.setMovementType(MovementType.OUT);
            dto.setQuantity(Math.abs(dto.getQuantity()));
        }

        return recordMovement(dto);
    }


    // ═══════════════════════════════════════════════════════
    //  📊 Query methods (read-only)
    // ═══════════════════════════════════════════════════════

    @Override
    @Transactional(readOnly = true)
    public List<StockMovementDTO> getByProduct(Long productId) {
        return movementRepo.findByProduct_ProductId(productId)
                .stream()
                .map(StockMovementMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockMovementDTO> getByWarehouse(Long warehouseId) {
        return movementRepo.findByWarehouse_WarehouseId(warehouseId)
                .stream()
                .map(StockMovementMapper::toDTO)
                .collect(Collectors.toList());
    }
}