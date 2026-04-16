package com.inventory.repository;

import com.inventory.entity.InventoryAudit;
import com.inventory.enums.AuditStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditRepository extends JpaRepository<InventoryAudit, Long> {

    List<InventoryAudit> findByProduct_ProductId(Long productId);

    List<InventoryAudit> findByWarehouse_WarehouseId(Long warehouseId);

    List<InventoryAudit> findByStatus(AuditStatus status);
}