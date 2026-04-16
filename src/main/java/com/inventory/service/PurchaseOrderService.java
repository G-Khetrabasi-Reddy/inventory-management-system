package com.inventory.service;

import com.inventory.dto.PurchaseOrderDTO;

public interface PurchaseOrderService {

    PurchaseOrderDTO createPO(PurchaseOrderDTO dto);

    PurchaseOrderDTO addItemsToPO(Long poId, PurchaseOrderDTO dto);

    PurchaseOrderDTO approvePO(Long poId);

    PurchaseOrderDTO receiveItems(Long poId, PurchaseOrderDTO dto);

    PurchaseOrderDTO getPOById(Long poId);
}