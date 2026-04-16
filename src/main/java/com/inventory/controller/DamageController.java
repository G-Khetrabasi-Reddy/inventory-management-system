package com.inventory.controller;

import com.inventory.dto.DamageDTO;
import com.inventory.service.DamageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/damage")
@RequiredArgsConstructor
public class DamageController {

    private final DamageService damageService;

    @PostMapping
    public DamageDTO reportDamage(@RequestBody DamageDTO dto) {
        return damageService.reportDamage(dto);
    }
}