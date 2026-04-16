package com.inventory.repository;

import com.inventory.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    // Find permission by exact name (e.g., "CREATE_PRODUCT")
    Optional<Permission> findByPermissionName(String permissionName);
}
