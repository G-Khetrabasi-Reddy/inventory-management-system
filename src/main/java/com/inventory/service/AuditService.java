package com.inventory.service;

import com.inventory.dto.AuditDTO;

public interface AuditService {

    AuditDTO performAudit(AuditDTO dto);
}