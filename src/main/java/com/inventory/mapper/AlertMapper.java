package com.inventory.mapper;

import com.inventory.dto.AlertDTO;
import com.inventory.entity.Alert;

public class AlertMapper {

    public static AlertDTO toDTO(Alert alert) {
        if (alert == null) return null;

        AlertDTO dto = new AlertDTO();

        dto.setAlertId(alert.getAlertId());
        dto.setAlertType(alert.getAlertType());
        dto.setMessage(alert.getMessage());
        dto.setThresholdValue(alert.getThresholdValue());
        dto.setCurrentValue(alert.getCurrentValue());
        dto.setPriority(alert.getPriority());
        dto.setIsResolved(alert.getIsResolved());
        dto.setResolvedAt(alert.getResolvedAt());
        dto.setCreatedAt(alert.getCreatedAt());

        if (alert.getProduct() != null)
            dto.setProductId(alert.getProduct().getProductId());

        if (alert.getWarehouse() != null)
            dto.setWarehouseId(alert.getWarehouse().getWarehouseId());

        if (alert.getResolvedBy() != null)
            dto.setResolvedBy(alert.getResolvedBy().getUserId());

        return dto;
    }
}