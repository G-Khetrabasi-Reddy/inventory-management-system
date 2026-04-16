package com.inventory.dto;

import com.inventory.enums.AuditStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditDTO {

    private Long auditId;

    private Long productId;
    private Long warehouseId;

    private Integer countedQuantity;
    private Integer systemQuantity;

    private LocalDateTime auditDate;

    private Long auditedBy;

    private AuditStatus status;
}