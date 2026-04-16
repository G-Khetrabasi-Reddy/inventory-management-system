package com.inventory.dto;

import com.inventory.enums.MovementType;
import com.inventory.enums.ReferenceType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementDTO {

    private Long movementId;

    private Long productId;
    private Long warehouseId;
    private Long destinationWarehouseId;

    private MovementType movementType;

    private Integer quantity;

    private ReferenceType referenceType;
    private Long referenceId;

    private Long performedBy;

    private LocalDateTime movementDate;
}