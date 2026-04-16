package com.inventory.service.impl;

import com.inventory.dto.*;
import com.inventory.entity.*;
import com.inventory.mapper.AdjustmentMapper;
import com.inventory.repository.*;
import com.inventory.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdjustmentServiceImpl implements AdjustmentService {

    private final AdjustmentRepository adjustmentRepo;
    private final AuditRepository auditRepo;
    private final ProductRepository productRepo;
    private final WarehouseRepository warehouseRepo;
    private final UserRepository userRepo;

    private final StockMovementService movementService;

    @Override
    public AdjustmentDTO adjustStock(AdjustmentDTO dto) {

        InventoryAudit audit = auditRepo.findById(dto.getAuditId())
                .orElseThrow(() -> new RuntimeException("Audit not found"));

        Product product = productRepo.findById(dto.getProductId()).orElseThrow();
        Warehouse warehouse = warehouseRepo.findById(dto.getWarehouseId()).orElseThrow();
        User user = userRepo.findById(dto.getAdjustedBy()).orElseThrow();

        // 🔥 Create movement
        StockMovementDTO movementDTO = new StockMovementDTO();
        movementDTO.setProductId(dto.getProductId());
        movementDTO.setWarehouseId(dto.getWarehouseId());
        movementDTO.setQuantity(dto.getAdjustmentQuantity());
        movementDTO.setPerformedBy(dto.getAdjustedBy());

        movementService.handleAdjustment(movementDTO);

        StockAdjustment adjustment = new StockAdjustment();
        adjustment.setProduct(product);
        adjustment.setWarehouse(warehouse);
        adjustment.setAudit(audit);
        adjustment.setAdjustmentQuantity(dto.getAdjustmentQuantity());
        adjustment.setReason(dto.getReason());
        adjustment.setAdjustedBy(user);

        return AdjustmentMapper.toDTO(adjustmentRepo.save(adjustment));
    }
}