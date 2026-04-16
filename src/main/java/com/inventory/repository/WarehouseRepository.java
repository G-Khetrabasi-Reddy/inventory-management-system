package com.inventory.repository;

import com.inventory.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    // 🔍 Find by name (useful for validation/search)
    Optional<Warehouse> findByWarehouseName(String warehouseName);

    // 🔍 Check existence
    boolean existsByWarehouseName(String warehouseName);
}