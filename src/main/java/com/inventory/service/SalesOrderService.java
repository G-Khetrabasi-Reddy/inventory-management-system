package com.inventory.service;

import com.inventory.dto.SalesOrderDTO;

import java.math.BigDecimal;

public interface SalesOrderService {

    SalesOrderDTO createOrder(Long customerId, Long userId);

    SalesOrderDTO addItem(Long soId, Long productId, int quantity, BigDecimal price);

    SalesOrderDTO confirmOrder(Long soId);

    SalesOrderDTO reserveStock(Long soId, Long warehouseId);

    SalesOrderDTO deliverOrder(Long soId);
}