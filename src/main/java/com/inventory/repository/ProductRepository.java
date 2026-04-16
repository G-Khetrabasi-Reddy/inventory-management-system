package com.inventory.repository;

import com.inventory.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 🔍 Find by SKU
    Optional<Product> findBySku(String sku);

    // 🔍 Check SKU exists (used in createProduct)
    boolean existsBySku(String sku);

    // 🔍 Active products only
    Page<Product> findByIsActiveTrue(Pageable pageable);

    // 🔍 By Category
    List<Product> findByCategoryCategoryId(Long categoryId);

    // 🔍 Low stock (important for alerts module later)
    List<Product> findByReorderLevelGreaterThan(Integer quantity);
}