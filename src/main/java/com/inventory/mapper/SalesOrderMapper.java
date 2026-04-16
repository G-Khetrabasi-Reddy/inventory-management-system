package com.inventory.mapper;

import com.inventory.dto.*;
import com.inventory.entity.*;
import com.inventory.enums.SalesStatus;

import java.util.stream.Collectors;

public class SalesOrderMapper {

    public static SalesOrderDTO toDTO(SalesOrder order) {
        return new SalesOrderDTO(
                order.getSoId(),
                order.getCustomer().getCustomerId(),
                order.getCreatedBy().getUserId(),
                order.getOrderDate(),
                order.getDeliveryDate(),
                order.getStatus().name(),
                order.getTotalAmount(),
                order.getNotes(),
                order.getItems() != null ?
                        order.getItems().stream()
                                .map(SalesOrderMapper::toItemDTO)
                                .collect(Collectors.toList())
                        : null
        );
    }

    public static SalesOrderItemDTO toItemDTO(SalesOrderItem item) {
        return new SalesOrderItemDTO(
                item.getProduct().getProductId(),
                item.getQuantityOrdered(),
                item.getQuantityDelivered(),
                item.getUnitPrice(),
                item.getTotalPrice()
        );
    }
}