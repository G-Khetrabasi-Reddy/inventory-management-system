package com.inventory.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderItemDTO {

    private Long productId;
    private Integer quantityOrdered;
    private Integer quantityDelivered;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}