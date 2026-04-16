package com.inventory.controller;

import com.inventory.dto.SupplierPaymentDTO;
import com.inventory.service.SupplierPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class SupplierPaymentController {

    private final SupplierPaymentService paymentService;

    // ==============================
    // 💳 MAKE PAYMENT
    // ==============================
    @PostMapping
    public SupplierPaymentDTO makePayment(@RequestBody SupplierPaymentDTO dto) {
        return paymentService.makePayment(dto);
    }

    // ==============================
    // 📄 GET PAYMENTS BY PO
    // ==============================
    @GetMapping("/po/{poId}")
    public List<SupplierPaymentDTO> getPaymentsByPO(@PathVariable Long poId) {
        return paymentService.getPaymentsByPO(poId);
    }
}