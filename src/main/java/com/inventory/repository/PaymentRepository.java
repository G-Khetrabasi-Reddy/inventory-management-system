package com.inventory.repository;

import com.inventory.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByInvoiceInvoiceId(Long invoiceId);

    List<Payment> findBySalesOrderSoId(Long soId);
}