package com.inventory.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private Long paymentId;
    private Long soId;
    private Long invoiceId;

    private BigDecimal amountPaid;
    private String paymentMethod;
    private String status;

    private LocalDate paymentDate;
}