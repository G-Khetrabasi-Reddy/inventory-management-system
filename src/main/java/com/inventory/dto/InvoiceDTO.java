package com.inventory.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {

    private Long invoiceId;
    private Long soId;
    private Long customerId;

    private LocalDate generatedDate;
    private LocalDate dueDate;

    private BigDecimal totalAmount;
    private String status;
}