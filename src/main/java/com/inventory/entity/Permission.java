package com.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

@Entity
@Table(name = "permissions",
        indexes = @Index(name = "idx_permission_name", columnList = "permissionName"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permissionId;

    @NotBlank
    @Column(nullable = false, unique = true, length = 100)
    private String permissionName;

    @Column(length = 255)
    private String description;
}