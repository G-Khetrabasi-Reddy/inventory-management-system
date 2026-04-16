package com.inventory.mapper;

import com.inventory.dto.PurchaseReturnDTO;
import com.inventory.entity.PurchaseReturn;

public class PurchaseReturnMapper {

    public static PurchaseReturnDTO toDTO(PurchaseReturn pr) {
        return new PurchaseReturnDTO(
                pr.getPurchaseOrder().getPoId(),
                pr.getProduct().getProductId(),
                pr.getWarehouse().getWarehouseId(),
                pr.getQuantity(),
                pr.getReason(),
                pr.getReturnedBy().getUserId(),
                pr.getStatus().name()
        );
    }
}