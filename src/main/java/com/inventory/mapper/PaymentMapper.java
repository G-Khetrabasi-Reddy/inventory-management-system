package com.inventory.mapper;

import com.inventory.dto.PaymentDTO;
import com.inventory.entity.Payment;

public class PaymentMapper {

    public static PaymentDTO toDTO(Payment payment) {
        return new PaymentDTO(
                payment.getPaymentId(),
                payment.getSalesOrder().getSoId(),
                payment.getInvoice().getInvoiceId(),
                payment.getAmountPaid(),
                payment.getPaymentMethod().name(),
                payment.getStatus().name(),
                payment.getPaymentDate()
        );
    }
}