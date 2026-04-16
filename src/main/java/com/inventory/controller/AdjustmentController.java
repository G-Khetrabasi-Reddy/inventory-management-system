package com.inventory.controller;

import com.inventory.dto.AdjustmentDTO;
import com.inventory.service.AdjustmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/adjustments")
@RequiredArgsConstructor
public class AdjustmentController {

    private final AdjustmentService adjustmentService;

    @PostMapping
    public AdjustmentDTO adjustStock(@RequestBody AdjustmentDTO dto) {
        return adjustmentService.adjustStock(dto);
    }
}