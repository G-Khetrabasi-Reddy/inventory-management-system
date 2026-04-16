package com.inventory.service.impl;

import com.inventory.dto.PurchaseReturnDTO;
import com.inventory.dto.StockMovementDTO;
import com.inventory.entity.*;
import com.inventory.enums.MovementType;
import com.inventory.enums.ReferenceType;
import com.inventory.enums.ReturnStatus;
import com.inventory.exceptions.*;
import com.inventory.mapper.PurchaseReturnMapper;
import com.inventory.repository.*;
import com.inventory.service.PurchaseReturnService;
import com.inventory.service.StockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseReturnServiceImpl implements PurchaseReturnService {

    private final PurchaseReturnRepository returnRepository;
    private final PurchaseOrderRepository poRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final UserRepository userRepository;
    private final StockMovementRepository movementRepository;  // for linking movement entity

    // 🔥 Injected Contract
    private final StockMovementService stockMovementService;

    // CRITICAL-12 FIX: returnToSupplier() now only creates the return record
    // with INITIATED status. Stock is NOT deducted until approveReturn() is called.
    @Override
    public PurchaseReturnDTO returnToSupplier(PurchaseReturnDTO dto) {

        PurchaseOrder po = poRepository.findById(dto.getPurchaseOrderId())
                .orElseThrow(() -> new PurchaseOrderNotFoundException("PO not found"));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found"));

        User user = userRepository.findById(dto.getReturnedBy())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        PurchaseOrderItem item = po.getItems().stream()
                .filter(i -> i.getProduct().getProductId().equals(dto.getProductId()))
                .findFirst()
                .orElseThrow(() -> new PurchaseReturnException("Product not found in PO"));

        if (dto.getQuantity() > item.getQuantityReceived()) {
            throw new PurchaseReturnException("Return quantity exceeds received quantity");
        }

        PurchaseReturn pr = new PurchaseReturn();
        pr.setPurchaseOrder(po);
        pr.setProduct(product);
        pr.setWarehouse(warehouse);
        pr.setQuantity(dto.getQuantity());
        pr.setReason(dto.getReason());
        pr.setReturnedBy(user);
        pr.setStatus(ReturnStatus.INITIATED);  // Pending approval — NO stock deduction yet

        pr = returnRepository.save(pr);

        return PurchaseReturnMapper.toDTO(pr);
    }

    // CRITICAL-12 FIX: New method — stock is only deducted after explicit approval
    public PurchaseReturnDTO approveReturn(Long returnId) {

        PurchaseReturn pr = returnRepository.findById(returnId)
                .orElseThrow(() -> new PurchaseReturnException("Purchase return not found: " + returnId));

        if (pr.getStatus() != ReturnStatus.INITIATED) {
            throw new PurchaseReturnException("Return must be in INITIATED status to approve");
        }

        // NOW trigger the stock deduction
        StockMovementDTO movementDTO = new StockMovementDTO();
        movementDTO.setProductId(pr.getProduct().getProductId());
        movementDTO.setWarehouseId(pr.getWarehouse().getWarehouseId());
        movementDTO.setQuantity(pr.getQuantity());
        movementDTO.setMovementType(MovementType.OUT);
        movementDTO.setReferenceType(ReferenceType.RETURN);
        movementDTO.setReferenceId(pr.getPoReturnId());
        movementDTO.setPerformedBy(pr.getReturnedBy().getUserId());

        StockMovementDTO result = stockMovementService.recordMovement(movementDTO);

        // IMP-13 FIX: Link the movement entity to the return record
        StockMovement movementEntity = movementRepository.getReferenceById(result.getMovementId());
        pr.setMovement(movementEntity);

        pr.setStatus(ReturnStatus.COMPLETED);

        return PurchaseReturnMapper.toDTO(returnRepository.save(pr));
    }
}