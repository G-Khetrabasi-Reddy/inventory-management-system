package com.inventory.repository;

import com.inventory.entity.SalesOrder;
import com.inventory.enums.SalesStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {

    List<SalesOrder> findByStatus(SalesStatus status);

    List<SalesOrder> findByCustomerCustomerId(Long customerId);
}