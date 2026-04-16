package com.inventory.dto;

import com.inventory.enums.PurchaseStatus;
import lombok.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDTO {

    private Long poId;

    @NotNull
    private Long supplierId;

    private Long createdBy;

    private LocalDate orderDate;
    private LocalDate expectedDate;
    private LocalDate receivedDate;

    private PurchaseStatus status;

    @DecimalMin("0.0")
    private BigDecimal totalAmount;

    private String notes;

    // 🔥 IMPORTANT: Nested Items
    private List<PurchaseOrderItemDTO> items;
}