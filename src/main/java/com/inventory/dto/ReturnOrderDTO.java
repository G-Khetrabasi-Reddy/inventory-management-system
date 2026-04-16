package com.inventory.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnOrderDTO {

    private Long returnId;

    private Long soId;
    private Long productId;
    private Long warehouseId;

    private Integer quantity;
    private Integer restockedQuantity;

    private String reason;
    private String status;

    private Long receivedBy;
    private LocalDate returnDate;
}