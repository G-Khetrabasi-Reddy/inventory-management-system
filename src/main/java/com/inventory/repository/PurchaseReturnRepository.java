package com.inventory.repository;

import com.inventory.entity.PurchaseReturn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseReturnRepository extends JpaRepository<PurchaseReturn, Long> {

    List<PurchaseReturn> findByPurchaseOrder_PoId(Long poId);

}