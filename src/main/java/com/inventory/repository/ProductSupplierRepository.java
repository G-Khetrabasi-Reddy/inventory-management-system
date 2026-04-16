package com.inventory.repository;

import com.inventory.entity.ProductSupplier;
import com.inventory.entity.ProductSupplierId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, ProductSupplierId> {

    // 🔍 All suppliers for a product
    List<ProductSupplier> findByProductProductId(Long productId);

    // 🔍 All products for a supplier
    List<ProductSupplier> findBySupplierSupplierId(Long supplierId);

    // 🔍 Preferred supplier for a product
    Optional<ProductSupplier> findByProductProductIdAndIsPreferredTrue(Long productId);

    // 🔍 Check mapping exists
    boolean existsByProductProductIdAndSupplierSupplierId(Long productId, Long supplierId);

    // 🔍 Active mappings only
    List<ProductSupplier> findByProductProductIdAndIsActiveTrue(Long productId);
}