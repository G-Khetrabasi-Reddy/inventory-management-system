package com.inventory.service.impl;

import com.inventory.dto.PaymentDTO;
import com.inventory.entity.*;
import com.inventory.enums.*;
import com.inventory.exceptions.*;
import com.inventory.mapper.PaymentMapper;
import com.inventory.repository.*;
import com.inventory.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentDTO processPayment(Long invoiceId, BigDecimal amount, String method) {

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found: " + invoiceId));

        // CRITICAL-10 FIX: Sum all existing payments for this invoice
        BigDecimal totalPaid = paymentRepository.findByInvoiceInvoiceId(invoiceId)
                .stream()
                .map(Payment::getAmountPaid)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal remaining = invoice.getTotalAmount().subtract(totalPaid);

        if (amount.compareTo(remaining) > 0) {
            throw new PaymentFailedException("Amount " + amount + " exceeds remaining balance " + remaining);
        }

        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setSalesOrder(invoice.getSalesOrder());
        payment.setAmountPaid(amount);
        payment.setPaymentMethod(PaymentMethod.valueOf(method));
        payment.setStatus(PaymentStatus.SUCCESS);

        paymentRepository.save(payment);

        // CRITICAL-10 FIX: Determine invoice status based on cumulative payments
        BigDecimal newTotal = totalPaid.add(amount);
        if (newTotal.compareTo(invoice.getTotalAmount()) >= 0) {
            invoice.setStatus(InvoiceStatus.PAID);
        } else {
            invoice.setStatus(InvoiceStatus.PARTIALLY_PAID);
        }

        invoiceRepository.save(invoice);

        return PaymentMapper.toDTO(payment);
    }
}