package com.inventory.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseReturnDTO {

    private Long purchaseOrderId;

    private Long productId;

    private Long warehouseId;

    private Integer quantity;

    private String reason;

    private Long returnedBy;

    private String status;
}