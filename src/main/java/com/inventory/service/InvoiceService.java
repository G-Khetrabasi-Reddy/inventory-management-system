package com.inventory.service;

import com.inventory.dto.InvoiceDTO;

public interface InvoiceService {

    InvoiceDTO generateInvoice(Long soId);
}