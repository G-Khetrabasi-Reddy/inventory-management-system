package com.inventory.mapper;

import com.inventory.dto.AdjustmentDTO;
import com.inventory.entity.StockAdjustment;

public class AdjustmentMapper {

    public static AdjustmentDTO toDTO(StockAdjustment entity) {
        if (entity == null) return null;

        AdjustmentDTO dto = new AdjustmentDTO();

        dto.setAdjustmentId(entity.getAdjustmentId());
        dto.setProductId(entity.getProduct().getProductId());
        dto.setWarehouseId(entity.getWarehouse().getWarehouseId());

        if (entity.getAudit() != null) {
            dto.setAuditId(entity.getAudit().getAuditId());
        }

        dto.setAdjustmentQuantity(entity.getAdjustmentQuantity());
        dto.setReason(entity.getReason());

        if (entity.getAdjustedBy() != null) {
            dto.setAdjustedBy(entity.getAdjustedBy().getUserId());
        }

        dto.setAdjustedAt(entity.getAdjustedAt());

        return dto;
    }
}