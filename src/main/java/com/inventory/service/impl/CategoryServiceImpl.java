package com.inventory.service.impl;

import com.inventory.dto.CategoryDTO;
import com.inventory.entity.Category;
import com.inventory.repository.CategoryRepository;
import com.inventory.service.CategoryService;
import com.inventory.mapper.CategoryMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryDTO createCategory(CategoryDTO dto) {

        Category parent = null;

        if (dto.getParentCategoryId() != null) {
            parent = categoryRepository.findById(dto.getParentCategoryId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
        }

        Category category = CategoryMapper.toEntity(dto, parent);

        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getCategoryHierarchy() {

        return categoryRepository.findByParentCategoryIsNull()
                .stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());

        return CategoryMapper.toDTO(categoryRepository.save(category));
    }
}