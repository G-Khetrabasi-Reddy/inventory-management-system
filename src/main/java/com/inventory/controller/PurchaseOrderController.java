package com.inventory.controller;

import com.inventory.dto.PurchaseOrderDTO;
import com.inventory.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    // ==============================
    // 🧾 CREATE PO
    // ==============================
    @PostMapping
    public PurchaseOrderDTO createPO(@RequestBody PurchaseOrderDTO dto) {
        return purchaseOrderService.createPO(dto);
    }

    // ==============================
    // 📦 ADD ITEMS
    // ==============================
    @PostMapping("/{id}/items")
    public PurchaseOrderDTO addItems(
            @PathVariable Long id,
            @RequestBody PurchaseOrderDTO dto) {
        return purchaseOrderService.addItemsToPO(id, dto);
    }

    // ==============================
    // ✅ APPROVE PO
    // ==============================
    @PutMapping("/{id}/approve")
    public PurchaseOrderDTO approvePO(@PathVariable Long id) {
        return purchaseOrderService.approvePO(id);
    }

    // ==============================
    // 📥 RECEIVE ITEMS
    // ==============================
    @PutMapping("/{id}/receive")
    public PurchaseOrderDTO receiveItems(
            @PathVariable Long id,
            @RequestBody PurchaseOrderDTO dto) {
        return purchaseOrderService.receiveItems(id, dto);
    }

    // ==============================
    // 🔍 GET PO
    // ==============================
    @GetMapping("/{id}")
    public PurchaseOrderDTO getPO(@PathVariable Long id) {
        return purchaseOrderService.getPOById(id);
    }
}