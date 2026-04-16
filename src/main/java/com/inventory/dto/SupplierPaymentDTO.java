package com.inventory.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierPaymentDTO {

    private Long purchaseOrderId;

    private Long paidBy;

    private BigDecimal amountPaid;

    private String paymentMethod;

    private String status;

    private LocalDate paymentDate;
}