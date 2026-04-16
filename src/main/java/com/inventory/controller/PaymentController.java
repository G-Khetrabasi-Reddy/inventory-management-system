package com.inventory.controller;

import com.inventory.dto.PaymentDTO;
import com.inventory.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentDTO processPayment(
            @RequestParam Long invoiceId,
            @RequestParam BigDecimal amount,
            @RequestParam String method
    ) {
        return paymentService.processPayment(invoiceId, amount, method);
    }
}