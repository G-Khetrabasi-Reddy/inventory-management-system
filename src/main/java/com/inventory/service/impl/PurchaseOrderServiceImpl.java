package com.inventory.service.impl;

import com.inventory.dto.*;
import com.inventory.entity.*;
import com.inventory.enums.MovementType;
import com.inventory.enums.PurchaseStatus;
import com.inventory.enums.ReferenceType;
import com.inventory.exceptions.*;
import com.inventory.mapper.PurchaseOrderMapper;
import com.inventory.repository.*;
import com.inventory.service.PurchaseOrderService;
import com.inventory.service.StockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository poRepository;
    private final PurchaseOrderItemRepository itemRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final WarehouseRepository warehouseRepository; // ✅ Added

    private final StockMovementService stockMovementService;

    @Override
    public PurchaseOrderDTO createPO(PurchaseOrderDTO dto) {
        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new SupplierNotFoundException("Supplier not found"));

        User user = userRepository.findById(dto.getCreatedBy())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        PurchaseOrder po = new PurchaseOrder();
        po.setSupplier(supplier);
        po.setCreatedBy(user);
        po.setStatus(PurchaseStatus.PENDING);
        po.setTotalAmount(BigDecimal.ZERO);

        return PurchaseOrderMapper.toDTO(poRepository.save(po));
    }

    @Override
    public PurchaseOrderDTO addItemsToPO(Long poId, PurchaseOrderDTO dto) {
        PurchaseOrder po = getPOEntity(poId);

        List<PurchaseOrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (PurchaseOrderItemDTO itemDTO : dto.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            // ✅ Fetch destination warehouse
            Warehouse warehouse = warehouseRepository.findById(itemDTO.getWarehouseId())
                    .orElseThrow(() -> new WarehouseNotFoundException("Warehouse ID required for PO items"));

            PurchaseOrderItem item = new PurchaseOrderItem();
            item.setPurchaseOrder(po);
            item.setProduct(product);
            item.setWarehouse(warehouse); // ✅ Save to entity
            item.setQuantityOrdered(itemDTO.getQuantityOrdered());
            item.setUnitCost(itemDTO.getUnitCost());

            item.initializePricing(itemDTO.getQuantityOrdered(), itemDTO.getUnitCost());
            total = total.add(item.getTotalCost());
            items.add(item);
        }

        po.getItems().clear();
        po.getItems().addAll(items);
        po.setTotalAmount(total);

        return PurchaseOrderMapper.toDTO(poRepository.save(po));
    }

    @Override
    public PurchaseOrderDTO approvePO(Long poId) {
        PurchaseOrder po = getPOEntity(poId);

        if (po.getItems() == null || po.getItems().isEmpty()) {
            throw new InvalidPurchaseOperationException("Cannot approve PO without items");
        }

        // CRITICAL-05 FIX: Route through StockMovementService instead of calling
        // StockService directly, so every stock change has an audit trail
        for (PurchaseOrderItem item : po.getItems()) {
            StockMovementDTO movementDTO = new StockMovementDTO();
            movementDTO.setProductId(item.getProduct().getProductId());
            movementDTO.setWarehouseId(item.getWarehouse().getWarehouseId());
            movementDTO.setQuantity(item.getQuantityOrdered());
            movementDTO.setMovementType(MovementType.ON_ORDER);
            movementDTO.setReferenceType(ReferenceType.PURCHASE);
            movementDTO.setReferenceId(po.getPoId());
            movementDTO.setPerformedBy(po.getCreatedBy().getUserId());

            stockMovementService.recordMovement(movementDTO);
        }

        po.setStatus(PurchaseStatus.APPROVED);
        return PurchaseOrderMapper.toDTO(poRepository.save(po));
    }

    @Override
    public PurchaseOrderDTO receiveItems(Long poId, PurchaseOrderDTO dto) {
        PurchaseOrder po = getPOEntity(poId);

        if (po.getStatus() != PurchaseStatus.APPROVED &&
                po.getStatus() != PurchaseStatus.PARTIALLY_RECEIVED) {
            throw new InvalidPurchaseOperationException("PO not approved for receiving");
        }

        for (PurchaseOrderItemDTO itemDTO : dto.getItems()) {
            PurchaseOrderItem item = po.getItems().stream()
                    .filter(i -> i.getProduct().getProductId().equals(itemDTO.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new InvalidPurchaseOperationException("Item not found in PO"));

            int newlyReceived = itemDTO.getQuantityReceived();
            int totalReceived = item.getQuantityReceived() + newlyReceived;

            if (totalReceived > item.getQuantityOrdered()) {
                throw new InvalidPurchaseOperationException("Receiving more than ordered for product: " + item.getProduct().getSku());
            }

            // Fallback to the saved item warehouse if not provided in the receive DTO
            Long destWarehouseId = itemDTO.getWarehouseId() != null ? itemDTO.getWarehouseId() : item.getWarehouse().getWarehouseId();

            item.setQuantityReceived(totalReceived);

            // 🔥 TRIGGER STOCK MOVEMENT MODULE (This inherently reduces quantityOnOrder via StockServiceImpl)
            StockMovementDTO movementDTO = new StockMovementDTO();
            movementDTO.setProductId(item.getProduct().getProductId());
            movementDTO.setWarehouseId(destWarehouseId);
            movementDTO.setQuantity(newlyReceived);
            movementDTO.setMovementType(MovementType.IN);
            movementDTO.setReferenceType(ReferenceType.PURCHASE);
            movementDTO.setReferenceId(po.getPoId());
            movementDTO.setPerformedBy(dto.getCreatedBy());

            stockMovementService.recordMovement(movementDTO);
        }

        // Update PO Status
        boolean allReceived = po.getItems().stream()
                .allMatch(i -> i.getQuantityReceived().equals(i.getQuantityOrdered()));

        po.setStatus(allReceived ? PurchaseStatus.RECEIVED : PurchaseStatus.PARTIALLY_RECEIVED);

        return PurchaseOrderMapper.toDTO(poRepository.save(po));
    }

    @Override
    public PurchaseOrderDTO getPOById(Long poId) {
        return PurchaseOrderMapper.toDTO(getPOEntity(poId));
    }

    private PurchaseOrder getPOEntity(Long poId) {
        return poRepository.findById(poId)
                .orElseThrow(() -> new PurchaseOrderNotFoundException("PO not found"));
    }
}