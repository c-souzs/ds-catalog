package com.souzs.ds_catalog.services;

import com.souzs.ds_catalog.dtos.CategoryDTO;
import com.souzs.ds_catalog.entities.Category;
import com.souzs.ds_catalog.repositories.CategoryRepository;
import com.souzs.ds_catalog.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    // Envolve o metodo de um contexto de transacao, com o rollback, commit e etc.
    // Como e um operacao de leitura, o readOnly, aberto para leitura, evita o locking do banco,
    // evita travar o banco apenar para leitura.
    @Transactional(readOnly = true)
    public List<CategoryDTO> searchAll() {
        List<Category> result = categoryRepository.findAll();

        return result.stream().map(category -> new CategoryDTO(category)).toList();
    }


    @Transactional(readOnly = true)
    public CategoryDTO searchById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Categoria não encontrada.")
        );

        return new CategoryDTO(category);
    }
}
