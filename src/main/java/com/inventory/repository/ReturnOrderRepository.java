package com.inventory.repository;

import com.inventory.entity.ReturnOrder;
import com.inventory.enums.ReturnStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReturnOrderRepository extends JpaRepository<ReturnOrder, Long> {

    List<ReturnOrder> findBySalesOrderSoId(Long soId);

    List<ReturnOrder> findByStatus(ReturnStatus status);
}