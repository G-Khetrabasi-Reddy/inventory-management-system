package com.inventory.service;

import com.inventory.dto.StockMovementDTO;

import java.util.List;

public interface StockMovementService {

    StockMovementDTO recordMovement(StockMovementDTO dto);

    StockMovementDTO handlePurchase(StockMovementDTO dto);

    StockMovementDTO handleSale(StockMovementDTO dto);

    StockMovementDTO handleTransfer(StockMovementDTO dto);

    StockMovementDTO handleDamage(StockMovementDTO dto);

    StockMovementDTO handleAdjustment(StockMovementDTO dto);

    List<StockMovementDTO> getByProduct(Long productId);

    List<StockMovementDTO> getByWarehouse(Long warehouseId);
}