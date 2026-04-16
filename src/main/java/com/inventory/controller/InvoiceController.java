package com.inventory.controller;

import com.inventory.dto.InvoiceDTO;
import com.inventory.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public InvoiceDTO generateInvoice(@RequestParam Long soId) {
        return invoiceService.generateInvoice(soId);
    }
}