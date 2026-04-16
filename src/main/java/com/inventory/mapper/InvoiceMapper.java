package com.inventory.mapper;

import com.inventory.dto.InvoiceDTO;
import com.inventory.entity.Invoice;

public class InvoiceMapper {

    public static InvoiceDTO toDTO(Invoice invoice) {
        return new InvoiceDTO(
                invoice.getInvoiceId(),
                invoice.getSalesOrder().getSoId(),
                invoice.getCustomer().getCustomerId(),
                invoice.getGeneratedDate(),
                invoice.getDueDate(),
                invoice.getTotalAmount(),
                invoice.getStatus().name()
        );
    }
}