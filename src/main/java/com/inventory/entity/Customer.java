package com.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Email;


@Entity
@Table(name = "customers",
        indexes = {
                @Index(name = "idx_customer_name", columnList = "customerName"),
                @Index(name = "idx_customer_email", columnList = "email")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends  BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String customerName;

    @Column(length = 100)
    private String contactPerson;

    @Column(length = 15)
    private String phone;

    @Email
    @Column(length = 100)
    private String email;

    @Column(length = 255)
    private String address;

    @Column(nullable = false)
    private Boolean isActive = true;
}
