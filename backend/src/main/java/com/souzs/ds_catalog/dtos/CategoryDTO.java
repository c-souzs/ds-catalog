package com.souzs.ds_catalog.dtos;

import com.souzs.ds_catalog.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CategoryDTO {
    private Long id;
    private String name;

    public CategoryDTO (Category entity) {
        id = entity.getId();
        name = entity.getName();
    }
}
