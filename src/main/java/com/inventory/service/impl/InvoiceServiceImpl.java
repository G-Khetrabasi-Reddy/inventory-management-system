package com.inventory.service.impl;

import com.inventory.dto.InvoiceDTO;
import com.inventory.entity.*;
import com.inventory.enums.InvoiceStatus;
import com.inventory.exceptions.*;
import com.inventory.mapper.InvoiceMapper;
import com.inventory.repository.*;
import com.inventory.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final SalesOrderRepository salesOrderRepository;
    private final InvoiceRepository invoiceRepository;

    @Override
    public InvoiceDTO generateInvoice(Long soId) {

        SalesOrder order = salesOrderRepository.findById(soId)
                .orElseThrow(() -> new SalesOrderNotFoundException("Sales Order not found: " + soId));

        Invoice invoice = new Invoice();
        invoice.setSalesOrder(order);
        invoice.setCustomer(order.getCustomer());
        invoice.setTotalAmount(order.getTotalAmount());
        invoice.setDueDate(LocalDate.now().plusDays(7));
        invoice.setStatus(InvoiceStatus.GENERATED);

        invoiceRepository.save(invoice);

        return InvoiceMapper.toDTO(invoice);
    }
}