package com.inventory.service;

import com.inventory.dto.PaymentDTO;

import java.math.BigDecimal;

public interface PaymentService {

    PaymentDTO processPayment(Long invoiceId, BigDecimal amount, String method);
}