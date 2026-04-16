package com.inventory.controller;

import com.inventory.dto.SalesOrderDTO;
import com.inventory.service.SalesOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/sales-orders")
@RequiredArgsConstructor
public class SalesOrderController {

    private final SalesOrderService salesOrderService;

    // ✅ Create Order
    @PostMapping
    public SalesOrderDTO createOrder(
            @RequestParam Long customerId,
            @RequestParam Long userId
    ) {
        return salesOrderService.createOrder(customerId, userId);
    }

    // ✅ Add Item
    @PostMapping("/{soId}/items")
    public SalesOrderDTO addItem(
            @PathVariable Long soId,
            @RequestParam Long productId,
            @RequestParam int quantity,
            @RequestParam BigDecimal price
    ) {
        return salesOrderService.addItem(soId, productId, quantity, price);
    }

    // ✅ Confirm Order
    @PutMapping("/{soId}/confirm")
    public SalesOrderDTO confirmOrder(@PathVariable Long soId) {
        return salesOrderService.confirmOrder(soId);
    }

    // 🔥 Reserve Stock
    @PutMapping("/{soId}/reserve")
    public SalesOrderDTO reserveStock(@PathVariable Long soId, @RequestParam Long warehouseId) {
        return salesOrderService.reserveStock(soId, warehouseId);
    }

    // 🚚 Deliver Order
    @PutMapping("/{soId}/deliver")
    public SalesOrderDTO deliverOrder(@PathVariable Long soId) {
        return salesOrderService.deliverOrder(soId);
    }
}