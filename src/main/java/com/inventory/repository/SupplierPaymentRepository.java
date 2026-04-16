package com.inventory.repository;

import com.inventory.entity.SupplierPayment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierPaymentRepository extends JpaRepository<SupplierPayment, Long> {

    // ✅ FIX FOR ISSUE 7: Prevent N+1 queries by eager fetching relations used in the Mapper
    // This fetches the SupplierPayment, the User (paidBy), and the PurchaseOrder in a single SQL JOIN query.
    @EntityGraph(attributePaths = {"paidBy", "purchaseOrder"})
    List<SupplierPayment> findByPurchaseOrder_PoId(Long poId);

}