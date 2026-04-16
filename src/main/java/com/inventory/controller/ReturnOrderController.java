package com.inventory.controller;

import com.inventory.dto.ReturnOrderDTO;
import com.inventory.service.ReturnOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/returns")
@RequiredArgsConstructor
public class ReturnOrderController {

    private final ReturnOrderService returnOrderService;

    @PostMapping
    public ReturnOrderDTO processReturn(
            @RequestParam Long soId,
            @RequestParam Long productId,
            @RequestParam Long warehouseId, // ✅ ADDED
            @RequestParam int quantity,
            @RequestParam String reason
    ) {
        return returnOrderService.processReturn(soId, productId,warehouseId, quantity, reason);
    }
}