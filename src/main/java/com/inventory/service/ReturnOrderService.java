package com.inventory.service;

import com.inventory.dto.ReturnOrderDTO;

public interface ReturnOrderService {

    public ReturnOrderDTO processReturn(Long soId, Long productId, Long warehouseId, int qty, String reason);
}