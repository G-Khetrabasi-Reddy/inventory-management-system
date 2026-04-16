package com.inventory.service;

import com.inventory.dto.AlertDTO;
import com.inventory.enums.AlertType;

import java.util.List;

public interface AlertService {

    void checkLowStock(Long productId, Long warehouseId);

    void checkOverStock(Long productId, Long warehouseId);

    void checkExpiry(Long productId);

    List<AlertDTO> getActiveAlerts();

    List<AlertDTO> getAlertsByType(AlertType type);

    AlertDTO resolveAlert(Long alertId, Long userId);
}