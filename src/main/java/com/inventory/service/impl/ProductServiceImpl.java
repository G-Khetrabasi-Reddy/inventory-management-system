package com.inventory.service.impl;

import com.inventory.dto.ProductDTO;
import com.inventory.entity.Category;
import com.inventory.entity.Product;
import com.inventory.exceptions.ProductNotFoundException;
import com.inventory.repository.CategoryRepository;
import com.inventory.repository.ProductRepository;
import com.inventory.service.ProductService;
import com.inventory.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    // 🔥 CREATE PRODUCT
    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO dto) {

        // 1. SKU validation
        if (productRepository.existsBySku(dto.getSku())) {
            throw new RuntimeException("SKU already exists");
        }

        // 2. Fetch category
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // 3. Business validation
        if (dto.getCostPrice() != null && dto.getUnitPrice() != null &&
                dto.getCostPrice().compareTo(dto.getUnitPrice()) > 0) {
            throw new RuntimeException("Cost price cannot be greater than selling price");
        }

        // 4. Map
        Product product = ProductMapper.toEntity(dto, category);

        // 5. Save
        Product saved = productRepository.save(product);

        return ProductMapper.toDTO(saved);
    }

    // 🔥 UPDATE PRODUCT
    @Override
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO dto) {

        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        // SKU update check
        if (!existing.getSku().equals(dto.getSku()) &&
                productRepository.existsBySku(dto.getSku())) {
            throw new RuntimeException("SKU already exists");
        }

        // Category fetch
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Update fields
        existing.setProductName(dto.getProductName());
        existing.setSku(dto.getSku());
        existing.setCategory(category);

        existing.setUnitPrice(dto.getUnitPrice());
        existing.setCostPrice(dto.getCostPrice());

        existing.setReorderLevel(dto.getReorderLevel());
        existing.setReorderQuantity(dto.getReorderQuantity());

        existing.setExpiryDate(dto.getExpiryDate());
        existing.setUnitOfMeasure(dto.getUnitOfMeasure());

        Product updated = productRepository.save(existing);

        return ProductMapper.toDTO(updated);
    }

    // 🔥 GET BY ID
    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return ProductMapper.toDTO(product);
    }

    // 🔥 GET ALL
    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllProducts(Pageable pageable) {

        return productRepository.findByIsActiveTrue(pageable)
                .map(ProductMapper::toDTO);
    }

    // 🔥 SOFT DELETE
    @Override
    @Transactional
    public void deactivateProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        product.setIsActive(false);
        productRepository.save(product);
    }
}