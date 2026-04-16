package com.inventory.dto;

import lombok.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderItemDTO {

    @NotNull
    private Long productId;

    private Long warehouseId;

    @NotNull
    @Min(1)
    private Integer quantityOrdered;

    private Integer quantityReceived;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal unitCost;

    private BigDecimal totalCost;
}