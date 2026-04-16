package com.inventory.mapper;

import com.inventory.dto.SupplierPaymentDTO;
import com.inventory.entity.SupplierPayment;

public class SupplierPaymentMapper {

    public static SupplierPaymentDTO toDTO(SupplierPayment payment) {
        return new SupplierPaymentDTO(
                payment.getPurchaseOrder().getPoId(),
                payment.getPaidBy().getUserId(),
                payment.getAmountPaid(),
                payment.getPaymentMethod().name(),
                payment.getStatus().name(),
                payment.getPaymentDate()
        );
    }
}