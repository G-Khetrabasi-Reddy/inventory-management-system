package com.inventory.repository;

import com.inventory.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    // 🔍 Active suppliers
    List<Supplier> findByIsActiveTrue();

    // 🔍 Optional: find by email (if unique)
    boolean existsByEmail(String email);
}