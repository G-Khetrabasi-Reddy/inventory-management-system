package com.inventory.repository;

import com.inventory.entity.Invoice;
import com.inventory.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findBySalesOrderSoId(Long soId);

    List<Invoice> findByStatus(InvoiceStatus status);
}