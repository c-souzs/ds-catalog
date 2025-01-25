package com.souzs.ds_catalog.resources;

import com.souzs.ds_catalog.dtos.CategoryDTO;
import com.souzs.ds_catalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryResource {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> searchAll() {
        List<CategoryDTO> result = categoryService.searchAll();

        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> searchById(
            @PathVariable Long id
    ) {
        CategoryDTO result = categoryService.searchById(id);

        return ResponseEntity.ok().body(result);
    }
}
