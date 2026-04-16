package com.inventory.service.impl;

import com.inventory.dto.SupplierPaymentDTO;
import com.inventory.entity.*;
import com.inventory.enums.PaymentMethod;
import com.inventory.enums.PaymentStatus;
import com.inventory.exceptions.*;
import com.inventory.mapper.SupplierPaymentMapper;
import com.inventory.repository.*;
import com.inventory.service.SupplierPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierPaymentServiceImpl implements SupplierPaymentService {

    private final SupplierPaymentRepository paymentRepository;
    private final PurchaseOrderRepository poRepository;
    private final UserRepository userRepository;

    // ==============================
    // 💳 1. MAKE PAYMENT
    // ==============================
    @Override
    public SupplierPaymentDTO makePayment(SupplierPaymentDTO dto) {

        PurchaseOrder po = poRepository.findById(dto.getPurchaseOrderId())
                .orElseThrow(() -> new PurchaseOrderNotFoundException("PO not found"));

        User user = userRepository.findById(dto.getPaidBy())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // 🔥 VALIDATION: total paid should not exceed PO amount
        List<SupplierPayment> existingPayments = paymentRepository.findByPurchaseOrder_PoId(po.getPoId());

        BigDecimal totalPaid = existingPayments.stream()
                .map(SupplierPayment::getAmountPaid)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal newTotal = totalPaid.add(dto.getAmountPaid());

        if (newTotal.compareTo(po.getTotalAmount()) > 0) {
            throw new PaymentFailedException("Payment exceeds total PO amount");
        }

        SupplierPayment payment = new SupplierPayment();
        payment.setPurchaseOrder(po);
        payment.setPaidBy(user);
        payment.setAmountPaid(dto.getAmountPaid());

        // 🔥 Convert String → Enum
        payment.setPaymentMethod(PaymentMethod.valueOf(dto.getPaymentMethod()));
        payment.setStatus(PaymentStatus.valueOf(dto.getStatus()));

        payment = paymentRepository.save(payment);

        return SupplierPaymentMapper.toDTO(payment);
    }

    // ==============================
    // 📄 2. GET PAYMENTS BY PO
    // ==============================
    @Override
    public List<SupplierPaymentDTO> getPaymentsByPO(Long poId) {

        List<SupplierPayment> payments = paymentRepository.findByPurchaseOrder_PoId(poId);

        return payments.stream()
                .map(SupplierPaymentMapper::toDTO)
                .collect(Collectors.toList());
    }
}