package com.inventory.dto;

import com.inventory.enums.CapacityUnit;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseDTO {

    private Long warehouseId;

    private String warehouseName;

    private String location;

    private Long managerId;

    private BigDecimal capacity;

    private CapacityUnit capacityUnit;
}