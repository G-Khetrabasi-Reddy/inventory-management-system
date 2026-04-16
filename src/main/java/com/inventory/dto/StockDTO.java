package com.inventory.dto;

import com.inventory.enums.StockStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockDTO {

    private Long stockId;

    private Long productId;
    private Long warehouseId;

    private Integer quantityAvailable;
    private Integer quantityReserved;
    private Integer quantityOnOrder;

    private Integer reorderLevelOverride;
    private Integer reorderQtyOverride;

    private StockStatus stockStatus;
}