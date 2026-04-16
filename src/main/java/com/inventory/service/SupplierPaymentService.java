package com.inventory.service;

import com.inventory.dto.SupplierPaymentDTO;

import java.util.List;

public interface SupplierPaymentService {

    SupplierPaymentDTO makePayment(SupplierPaymentDTO dto);

    List<SupplierPaymentDTO> getPaymentsByPO(Long poId);
}