package com.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long categoryId;

    @NotBlank
    private String categoryName;

    private String description;

    private Long parentCategoryId; // 🔥 important

    private Boolean isActive = true;

    // 🔥 For hierarchy response
    private List<CategoryDTO> subCategories;
}