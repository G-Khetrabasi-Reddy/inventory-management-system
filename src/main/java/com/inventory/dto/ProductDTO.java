package com.inventory.dto;

import com.inventory.enums.UnitOfMeasure;
import jakarta.validation.constraints.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long productId;

    @NotBlank
    private String productName;

    @NotBlank
    private String sku;

    @NotNull
    private Long categoryId; // 🔥 Only ID (NOT full object)

    @DecimalMin("0.0")
    private BigDecimal unitPrice;

    @DecimalMin("0.0")
    private BigDecimal costPrice;

    @Min(0)
    private Integer reorderLevel;

    @Min(0)
    private Integer reorderQuantity;

    private LocalDate expiryDate;

    private UnitOfMeasure unitOfMeasure;

    private Boolean isActive = true;
}