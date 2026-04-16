package com.inventory.service;

import com.inventory.dto.PurchaseReturnDTO;

public interface PurchaseReturnService {

    PurchaseReturnDTO returnToSupplier(PurchaseReturnDTO dto);

    PurchaseReturnDTO approveReturn(Long returnId);
}