package com.inventory.mapper;

import com.inventory.dto.AuditDTO;
import com.inventory.entity.InventoryAudit;

public class AuditMapper {

    public static AuditDTO toDTO(InventoryAudit entity) {
        if (entity == null) return null;

        AuditDTO dto = new AuditDTO();

        dto.setAuditId(entity.getAuditId());
        dto.setProductId(entity.getProduct().getProductId());
        dto.setWarehouseId(entity.getWarehouse().getWarehouseId());

        dto.setCountedQuantity(entity.getCountedQuantity());
        dto.setSystemQuantity(entity.getSystemQuantity());

        dto.setAuditDate(entity.getAuditDate());

        if (entity.getAuditedBy() != null) {
            dto.setAuditedBy(entity.getAuditedBy().getUserId());
        }

        dto.setStatus(entity.getStatus());

        return dto;
    }
}