package com.inventory.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdjustmentDTO {

    private Long adjustmentId;

    private Long productId;
    private Long warehouseId;

    private Long auditId;

    private Integer adjustmentQuantity;

    private String reason;

    private Long adjustedBy;

    private LocalDateTime adjustedAt;
}