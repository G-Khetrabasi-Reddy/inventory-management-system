package com.inventory.controller;

import com.inventory.dto.SupplierDTO;
import com.inventory.service.SupplierService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    // 🔥 CREATE
    @PostMapping
    public ResponseEntity<SupplierDTO> createSupplier(@Valid @RequestBody SupplierDTO dto) {
        return ResponseEntity.ok(supplierService.createSupplier(dto));
    }

    // 🔥 GET ALL
    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    // 🔥 UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<SupplierDTO> updateSupplier(@PathVariable Long id,
                                                      @Valid @RequestBody SupplierDTO dto) {
        return ResponseEntity.ok(supplierService.updateSupplier(id, dto));
    }
}