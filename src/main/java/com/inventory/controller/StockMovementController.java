package com.inventory.controller;

import com.inventory.dto.StockMovementDTO;
import com.inventory.service.StockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockMovementService movementService;

    // 🔥 CREATE MOVEMENT (manual/admin)
    @PostMapping
    public StockMovementDTO createMovement(@RequestBody StockMovementDTO dto) {
        return movementService.recordMovement(dto);
    }

    // 📊 GET BY PRODUCT
    @GetMapping("/product/{productId}")
    public List<StockMovementDTO> getByProduct(@PathVariable Long productId) {
        return movementService.getByProduct(productId);
    }

    // 📊 GET BY WAREHOUSE
    @GetMapping("/warehouse/{warehouseId}")
    public List<StockMovementDTO> getByWarehouse(@PathVariable Long warehouseId) {
        return movementService.getByWarehouse(warehouseId);
    }
}