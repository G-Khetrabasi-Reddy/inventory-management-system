package com.inventory.controller;

import com.inventory.dto.WarehouseDTO;
import com.inventory.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    // ✅ CREATE
    @PostMapping
    public ResponseEntity<WarehouseDTO> createWarehouse(@RequestBody WarehouseDTO dto) {
        return ResponseEntity.ok(warehouseService.createWarehouse(dto));
    }

    // ✅ GET ALL
    @GetMapping
    public ResponseEntity<List<WarehouseDTO>> getAllWarehouses() {
        return ResponseEntity.ok(warehouseService.getAllWarehouses());
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<WarehouseDTO> getWarehouseById(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getWarehouseById(id));
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<WarehouseDTO> updateWarehouse(
            @PathVariable Long id,
            @RequestBody WarehouseDTO dto
    ) {
        return ResponseEntity.ok(warehouseService.updateWarehouse(id, dto));
    }
}