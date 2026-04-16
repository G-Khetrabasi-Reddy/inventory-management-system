package com.inventory.repository;

import com.inventory.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    // 🔥 CORE METHOD (MOST USED)
    Optional<Stock> findByProduct_ProductIdAndWarehouse_WarehouseId(
            Long productId, Long warehouseId
    );

    // 📦 All stock for a product (across warehouses)
    List<Stock> findByProduct_ProductId(Long productId);

    // 🏢 All stock in a warehouse
    List<Stock> findByWarehouse_WarehouseId(Long warehouseId);

    // ✅ Existence check (used in createStockIfNotExists)
    boolean existsByProduct_ProductIdAndWarehouse_WarehouseId(
            Long productId, Long warehouseId
    );
}