package com.inventory.controller;

import com.inventory.dto.PurchaseReturnDTO;
import com.inventory.service.PurchaseReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase-returns")
@RequiredArgsConstructor
public class PurchaseReturnController {

    private final PurchaseReturnService returnService;

    // ==============================
    // 🔁 RETURN TO SUPPLIER
    // ==============================
    @PostMapping
    public PurchaseReturnDTO returnToSupplier(@RequestBody PurchaseReturnDTO dto) {
        return returnService.returnToSupplier(dto);
    }
}