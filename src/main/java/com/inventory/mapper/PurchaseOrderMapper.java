package com.inventory.mapper;

import com.inventory.dto.*;
import com.inventory.entity.*;

import java.util.stream.Collectors;

public class PurchaseOrderMapper {

    public static PurchaseOrderDTO toDTO(PurchaseOrder po) {
        return new PurchaseOrderDTO(
                po.getPoId(),
                po.getSupplier().getSupplierId(),
                po.getCreatedBy().getUserId(),
                po.getOrderDate(),
                po.getExpectedDate(),
                po.getReceivedDate(),
                po.getStatus(),
                po.getTotalAmount(),
                po.getNotes(),
                po.getItems().stream().map(PurchaseOrderMapper::toItemDTO).collect(Collectors.toList())
        );
    }

    public static PurchaseOrderItemDTO toItemDTO(PurchaseOrderItem item) {
        return new PurchaseOrderItemDTO(
                item.getProduct().getProductId(),
                item.getWarehouse() != null ? item.getWarehouse().getWarehouseId() : null, // ✅ Map Warehouse ID
                item.getQuantityOrdered(),
                item.getQuantityReceived(),
                item.getUnitCost(),
                item.getTotalCost()
        );
    }
}