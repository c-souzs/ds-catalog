package com.souzs.ds_catalog.resources;

import com.souzs.ds_catalog.domain.dto.ProductDTO;
import com.souzs.ds_catalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductResource {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> searchAll(Pageable pageable) {
        Page<ProductDTO> result = productService.searchAll(pageable);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> searchAll(
            @PathVariable Long id
    ) {
        ProductDTO result = productService.searchById(id);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> add(
            @RequestBody ProductDTO dto
    ) {
        dto = productService.add(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.id()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(
            @PathVariable Long id,
            @RequestBody ProductDTO dto
    ) {
        dto = productService.update(id, dto);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(
            @PathVariable Long id
    ) {
        Long result = productService.delete(id);

        return ResponseEntity.ok().body(result);
    }
}
