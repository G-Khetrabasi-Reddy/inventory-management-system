package com.inventory.service;

import com.inventory.dto.SupplierDTO;

import java.util.List;

public interface SupplierService {

    SupplierDTO createSupplier(SupplierDTO dto);

    SupplierDTO updateSupplier(Long id, SupplierDTO dto);

    List<SupplierDTO> getAllSuppliers();
}