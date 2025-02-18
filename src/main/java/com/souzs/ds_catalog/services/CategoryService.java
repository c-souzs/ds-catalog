package com.souzs.ds_catalog.services;

import com.souzs.ds_catalog.domain.dto.CategoryDTO;
import com.souzs.ds_catalog.domain.entities.Category;
import com.souzs.ds_catalog.repository.CategoryRepository;
import com.souzs.ds_catalog.services.exceptions.DatabaseException;
import com.souzs.ds_catalog.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> searchAll(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);

        return categories.map(category -> new CategoryDTO(category));
    }

    @Transactional(readOnly = true)
    public CategoryDTO searchById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada."));

        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO add(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.name());

        category = categoryRepository.save(category);

        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try {
            Category category = categoryRepository.getReferenceById(id);
            category.setName(dto.name());

            category = categoryRepository.save(category);

            return new CategoryDTO(category);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Categoria não encontrada");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Long delete(Long id) {
        if(!categoryRepository.existsById(id)) throw new EntityNotFoundException("Categoria não encontrada.");

        try {
            categoryRepository.deleteById(id);
            return id;
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Essa categoria possui items associado a ela.");
        }
    }
}
