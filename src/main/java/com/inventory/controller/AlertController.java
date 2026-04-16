package com.inventory.controller;

import com.inventory.dto.AlertDTO;
import com.inventory.enums.AlertType;
import com.inventory.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    // 🔥 GET ALL ACTIVE ALERTS
    @GetMapping
    public List<AlertDTO> getActiveAlerts() {
        return alertService.getActiveAlerts();
    }

    // 🔥 FILTER ALERTS BY TYPE
    @GetMapping("/type/{type}")
    public List<AlertDTO> getAlertsByType(@PathVariable AlertType type) {
        return alertService.getAlertsByType(type);
    }

    // 🔥 RESOLVE ALERT
    @PutMapping("/{id}/resolve")
    public AlertDTO resolveAlert(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        return alertService.resolveAlert(id, userId);
    }
}