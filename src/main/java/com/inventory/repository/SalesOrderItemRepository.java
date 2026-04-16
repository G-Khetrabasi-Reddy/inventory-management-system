package com.inventory.repository;

import com.inventory.entity.SalesOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesOrderItemRepository extends JpaRepository<SalesOrderItem, Long> {

    List<SalesOrderItem> findBySalesOrderSoId(Long soId);

    boolean existsBySalesOrderSoIdAndProductProductId(Long soId, Long productId);
}