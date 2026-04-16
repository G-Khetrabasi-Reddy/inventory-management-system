package com.inventory.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DamageDTO {

    private Long damageId;

    private Long productId;
    private Long warehouseId;

    private Long movementId;

    private Integer quantity;

    private String reason;

    private LocalDateTime reportedAt;

    private Long reportedBy;
}