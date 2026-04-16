package com.inventory.dto;

import com.inventory.enums.AlertType;
import com.inventory.enums.Priority;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertDTO {

    private Long alertId;

    private Long productId;
    private Long warehouseId;

    private AlertType alertType;
    private String message;

    private Integer thresholdValue;
    private Integer currentValue;

    private Priority priority;

    private Boolean isResolved;

    private Long resolvedBy;
    private LocalDateTime resolvedAt;

    private LocalDateTime createdAt;
}