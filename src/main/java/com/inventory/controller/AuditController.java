package com.inventory.controller;

import com.inventory.dto.AuditDTO;
import com.inventory.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @PostMapping
    public AuditDTO performAudit(@RequestBody AuditDTO dto) {
        return auditService.performAudit(dto);
    }
}