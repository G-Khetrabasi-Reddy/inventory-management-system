package com.inventory.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDTO {

    private Long supplierId;

    @NotBlank
    private String supplierName;

    private String contactPerson;

    private String phone;

    @Email
    private String email;

    private String address;

    private Boolean isActive = true;
}