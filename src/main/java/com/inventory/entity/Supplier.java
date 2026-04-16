package com.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "suppliers",
        indexes = {
                @Index(name = "idx_supplier_name", columnList = "supplierName"),
                @Index(name = "idx_supplier_email", columnList = "email")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supplier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String supplierName;

    @Column(length = 100)
    private String contactPerson;

    @Column(length = 15)
    private String phone;

    @Email
    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 255)
    private String address;

    @Column(nullable = false)
    private Boolean isActive = true;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}