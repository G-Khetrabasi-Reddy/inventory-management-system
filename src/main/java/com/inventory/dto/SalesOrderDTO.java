package com.inventory.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderDTO {

    private Long soId;
    private Long customerId;
    private Long createdBy;

    private LocalDate orderDate;
    private LocalDate deliveryDate;

    private String status;
    private BigDecimal totalAmount;
    private String notes;

    private List<SalesOrderItemDTO> items;
}