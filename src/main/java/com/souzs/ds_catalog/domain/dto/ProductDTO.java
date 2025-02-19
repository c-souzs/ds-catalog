package com.souzs.ds_catalog.domain.dto;

import com.souzs.ds_catalog.domain.entities.Category;
import com.souzs.ds_catalog.domain.entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record ProductDTO(
        Long id,
        String name,
        String description,
        Double price,
        String imgUrl,
        List<CategoryDTO> categories
) {
    public ProductDTO(Product entity) {
        this(entity.getId(), entity.getName(), entity.getDescription(), entity.getPrice(), entity.getImgUrl(), entity.getCategories().stream().map(c -> new CategoryDTO(c)).toList());
    }
}
