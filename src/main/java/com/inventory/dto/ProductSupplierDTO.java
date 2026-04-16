package com.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSupplierDTO {

    @NotNull
    private Long productId;

    @NotNull
    private Long supplierId;

    private String supplierProductCode;

    @DecimalMin("0.0")
    private BigDecimal unitCost;

    @NotNull
    @Min(0)
    private Integer leadTimeDays;

    private Boolean isPreferred;

    private Boolean isActive = true;
}