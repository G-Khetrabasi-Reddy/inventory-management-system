package com.inventory.service;

import com.inventory.dto.StockDTO;

import java.util.List;

public interface StockService {

    StockDTO getStock(Long productId, Long warehouseId);

    List<StockDTO> getAllStockByProduct(Long productId);

    List<StockDTO> getAllStockByWarehouse(Long warehouseId);

    void createStockIfNotExists(Long productId, Long warehouseId);

    // 🔥 INTERNAL (used by Module 6)
    void updateStockQuantities(Long productId, Long warehouseId,
                               int available, int reserved, int onOrder);

    // 🔥 BUSINESS METHODS
    void reserveStock(Long productId, Long warehouseId, int quantity);

    void releaseStock(Long productId, Long warehouseId, int quantity);

    void addStock(Long productId, Long warehouseId, int quantity);

    void deductStock(Long productId, Long warehouseId, int quantity);

    void reduceAvailableStock(Long productId, Long warehouseId, int quantity);

    // Add this method signature
    void addQuantityOnOrder(Long productId, Long warehouseId, int quantity);
}