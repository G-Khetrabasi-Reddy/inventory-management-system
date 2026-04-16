package com.inventory.repository;

import com.inventory.entity.PurchaseOrder;
import com.inventory.enums.PurchaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    List<PurchaseOrder> findBySupplier_SupplierId(Long supplierId);

    List<PurchaseOrder> findByStatus(PurchaseStatus status);

}