package com.inventory.repository;

import com.inventory.entity.StockAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdjustmentRepository extends JpaRepository<StockAdjustment, Long> {

    List<StockAdjustment> findByProduct_ProductId(Long productId);

    List<StockAdjustment> findByWarehouse_WarehouseId(Long warehouseId);

    List<StockAdjustment> findByAudit_AuditId(Long auditId);
}