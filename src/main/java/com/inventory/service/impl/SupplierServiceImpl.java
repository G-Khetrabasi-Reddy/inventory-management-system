package com.inventory.service.impl;

import com.inventory.dto.SupplierDTO;
import com.inventory.entity.Supplier;
import com.inventory.repository.SupplierRepository;
import com.inventory.service.SupplierService;
import com.inventory.mapper.SupplierMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    @Transactional
    public SupplierDTO createSupplier(SupplierDTO dto) {

        Supplier supplier = SupplierMapper.toEntity(dto);
        return SupplierMapper.toDTO(supplierRepository.save(supplier));
    }

    @Override
    @Transactional
    public SupplierDTO updateSupplier(Long id, SupplierDTO dto) {

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        supplier.setSupplierName(dto.getSupplierName());
        supplier.setContactPerson(dto.getContactPerson());
        supplier.setPhone(dto.getPhone());
        supplier.setEmail(dto.getEmail());
        supplier.setAddress(dto.getAddress());

        return SupplierMapper.toDTO(supplierRepository.save(supplier));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupplierDTO> getAllSuppliers() {

        return supplierRepository.findByIsActiveTrue()
                .stream()
                .map(SupplierMapper::toDTO)
                .collect(Collectors.toList());
    }
}