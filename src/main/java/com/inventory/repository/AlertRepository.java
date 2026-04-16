package com.inventory.repository;

import com.inventory.entity.Alert;
import com.inventory.enums.AlertType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    // 🔥 Get all active alerts
    List<Alert> findByIsResolvedFalse();

    // 🔥 Filter by type
    List<Alert> findByAlertTypeAndIsResolvedFalse(AlertType alertType);

    // 🔥 Avoid duplicate alerts (IMPORTANT RULE)
    boolean existsByProduct_ProductIdAndWarehouse_WarehouseIdAndAlertTypeAndIsResolvedFalse(
            Long productId,
            Long warehouseId,
            AlertType alertType
    );
}