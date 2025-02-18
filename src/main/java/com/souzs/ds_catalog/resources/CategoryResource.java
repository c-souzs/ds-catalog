package com.souzs.ds_catalog.resources;

import com.souzs.ds_catalog.domain.dto.CategoryDTO;
import com.souzs.ds_catalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/categories")
public class CategoryResource {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> searchAll(Pageable pageable) {
        Page<CategoryDTO> result = categoryService.searchAll(pageable);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> searchAll(
            @PathVariable Long id
    ) {
        CategoryDTO result = categoryService.searchById(id);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> add(
            @RequestBody CategoryDTO dto
    ) {
        dto = categoryService.add(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.id()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(
            @PathVariable Long id,
            @RequestBody CategoryDTO dto
    ) {
        dto = categoryService.update(id, dto);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(
            @PathVariable Long id
    ) {
        Long result = categoryService.delete(id);

        return ResponseEntity.ok().body(result);
    }
}
