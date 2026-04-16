package com.inventory.service.impl;

import com.inventory.dto.AuditDTO;
import com.inventory.entity.*;
import com.inventory.enums.AuditStatus;
import com.inventory.mapper.AuditMapper;
import com.inventory.repository.*;
import com.inventory.service.AuditService;
import com.inventory.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepo;
    private final ProductRepository productRepo;
    private final WarehouseRepository warehouseRepo;
    private final UserRepository userRepo;
    private final StockService stockService;

    @Override
    public AuditDTO performAudit(AuditDTO dto) {

        Product product = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Warehouse warehouse = warehouseRepo.findById(dto.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        User user = userRepo.findById(dto.getAuditedBy())
                .orElseThrow(() -> new RuntimeException("User not found"));

        int systemQty = stockService
                .getStock(product.getProductId(), warehouse.getWarehouseId())
                .getQuantityAvailable();

        InventoryAudit audit = new InventoryAudit();
        audit.setProduct(product);
        audit.setWarehouse(warehouse);
        audit.setCountedQuantity(dto.getCountedQuantity());
        audit.setSystemQuantity(systemQty);
        audit.setAuditedBy(user);

        audit.setStatus(
                dto.getCountedQuantity() == systemQty
                        ? AuditStatus.MATCHED
                        : AuditStatus.MISMATCH
        );

        return AuditMapper.toDTO(auditRepo.save(audit));
    }
}