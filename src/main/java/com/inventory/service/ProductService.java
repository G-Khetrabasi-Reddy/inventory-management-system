package com.inventory.service;

import com.inventory.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductDTO createProduct(ProductDTO dto);

    ProductDTO updateProduct(Long id, ProductDTO dto);

    ProductDTO getProductById(Long id);

    Page<ProductDTO> getAllProducts(Pageable pageable);

    void deactivateProduct(Long id);
}