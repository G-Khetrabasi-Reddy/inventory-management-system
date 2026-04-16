package com.inventory.mapper;

import com.inventory.dto.ReturnOrderDTO;
import com.inventory.entity.ReturnOrder;

public class ReturnOrderMapper {

    public static ReturnOrderDTO toDTO(ReturnOrder r) {
        return new ReturnOrderDTO(
                r.getReturnId(),
                r.getSalesOrder().getSoId(),
                r.getProduct().getProductId(),
                r.getWarehouse().getWarehouseId(),
                r.getQuantity(),
                r.getRestockedQuantity(),
                r.getReason(),
                r.getStatus().name(),
                r.getReceivedBy() != null ? r.getReceivedBy().getUserId() : null,
                r.getReturnDate()
        );
    }
}