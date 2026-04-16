package com.inventory.mapper;

import com.inventory.dto.DamageDTO;
import com.inventory.entity.DamageRecord;

public class DamageMapper {

    public static DamageDTO toDTO(DamageRecord entity) {
        if (entity == null) return null;

        DamageDTO dto = new DamageDTO();

        dto.setDamageId(entity.getDamageId());
        dto.setProductId(entity.getProduct().getProductId());
        dto.setWarehouseId(entity.getWarehouse().getWarehouseId());

        if (entity.getMovement() != null) {
            dto.setMovementId(entity.getMovement().getMovementId());
        }

        dto.setQuantity(entity.getQuantity());
        dto.setReason(entity.getReason());
        dto.setReportedAt(entity.getReportedAt());

        if (entity.getReportedBy() != null) {
            dto.setReportedBy(entity.getReportedBy().getUserId());
        }

        return dto;
    }
}