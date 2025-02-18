package com.souzs.ds_catalog.domain.dto;

import com.souzs.ds_catalog.domain.entities.Category;

public record CategoryDTO(Long id, String name) {
    public CategoryDTO(Category category) {
        this(category.getId(), category.getName());
    }
}
