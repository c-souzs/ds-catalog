package com.souzs.ds_catalog.services;

import com.souzs.ds_catalog.domain.dto.ProductDTO;
import com.souzs.ds_catalog.domain.entities.Category;
import com.souzs.ds_catalog.domain.entities.Product;
import com.souzs.ds_catalog.repository.CategoryRepository;
import com.souzs.ds_catalog.repository.ProductRepository;
import com.souzs.ds_catalog.services.exceptions.DatabaseException;
import com.souzs.ds_catalog.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> searchAll(Pageable pageable) {
        Page<Product> categories = productRepository.findAll(pageable);

        return categories.map(Product -> new ProductDTO(Product));
    }

    @Transactional(readOnly = true)
    public ProductDTO searchById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));

        return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO add(ProductDTO dto) {
        Product product = new Product();

        copyEntityToDto(product, dto);

        product = productRepository.save(product);

        return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product product = productRepository.getReferenceById(id);
            copyEntityToDto(product, dto);

            product = productRepository.save(product);

            return new ProductDTO(product);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Produto não encontrado.");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Long delete(Long id) {
        if(!productRepository.existsById(id)) throw new EntityNotFoundException("Produto não encontrado.");

        try {
            productRepository.deleteById(id);
            return id;
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Esse produto possui items associado a ele.");
        }
    }

    private void copyEntityToDto(Product entity, ProductDTO dto) {
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setPrice(dto.price());
        entity.setImgUrl(dto.imgUrl());

        dto.categories().forEach(categoryDto -> {
            Category category = categoryRepository.getReferenceById(categoryDto.id());

            entity.getCategories().add(category);
        });
    }
}
