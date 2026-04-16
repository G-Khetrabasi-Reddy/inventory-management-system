package com.inventory.controller;

import com.inventory.dto.StockDTO;
import com.inventory.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    // ✅ GET stock by product + warehouse
    @GetMapping
    public ResponseEntity<StockDTO> getStock(
            @RequestParam Long productId,
            @RequestParam Long warehouseId
    ) {
        return ResponseEntity.ok(
                stockService.getStock(productId, warehouseId)
        );
    }

    // ✅ GET all stock by product
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<StockDTO>> getStockByProduct(
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(
                stockService.getAllStockByProduct(productId)
        );
    }

    // ✅ GET all stock by warehouse
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<StockDTO>> getStockByWarehouse(
            @PathVariable Long warehouseId
    ) {
        return ResponseEntity.ok(
                stockService.getAllStockByWarehouse(warehouseId)
        );
    }
}