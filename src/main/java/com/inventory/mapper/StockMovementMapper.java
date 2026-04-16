package com.inventory.mapper;

import com.inventory.dto.StockMovementDTO;
import com.inventory.entity.*;

public class StockMovementMapper {

    // ENTITY → DTO
    public static StockMovementDTO toDTO(StockMovement entity) {
        if (entity == null) return null;

        StockMovementDTO dto = new StockMovementDTO();

        dto.setMovementId(entity.getMovementId());
        dto.setProductId(entity.getProduct().getProductId());
        dto.setWarehouseId(entity.getWarehouse().getWarehouseId());

        if (entity.getDestinationWarehouse() != null) {
            dto.setDestinationWarehouseId(
                    entity.getDestinationWarehouse().getWarehouseId()
            );
        }

        dto.setMovementType(entity.getMovementType());
        dto.setQuantity(entity.getQuantity());
        dto.setReferenceType(entity.getReferenceType());
        dto.setReferenceId(entity.getReferenceId());

        if (entity.getPerformedBy() != null) {
            dto.setPerformedBy(entity.getPerformedBy().getUserId());
        }

        dto.setMovementDate(entity.getMovementDate());

        return dto;
    }
}