package com.souzs.ds_catalog.repositories;

import com.souzs.ds_catalog.domain.entities.Category;
import com.souzs.ds_catalog.domain.entities.Product;
import com.souzs.ds_catalog.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

// Carrega apenas os recursos da JPA, nao carregando os recursos dos controllers ou services.
// Nesse caso estamos fazendo testes de integracao, pois ele carrega o contexto da JPA.
// Esse teste leva em considerao os dados do banco H2.
@DataJpaTest
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;

    private Product product;
    private int countTotalProducts;
    private Pageable pageable;

    @BeforeEach
    void setUp() throws Exception {
        countTotalProducts = 25;
        product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png");

        Category category = new Category();
        category.setId(1L);

        product.getCategories().add(category);

        pageable = PageRequest.of(0, 10);
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        // Arrange
        long idDelete = 1L;

        // Act
        productRepository.deleteById(idDelete);

        Optional<Product> result = productRepository.findById(idDelete);

        // Assert
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void saveShouldPersistWhitAutoincrementWhenIdIsNull() {
        // Act
        product.setId(null);
        product = productRepository.save(product);

        // Asserts
        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());
    }

    @Test
    public void saveShouldPersistObjectWithNewDataWhenIdNotNull() {
        // Assert
        String newName = product.getName() + " Updated.";
        double newPrice = 1000.00;

        // Act
        product.setName(newName);
        product.setPrice(newPrice);
        product = productRepository.save(product);

        // Assertions
        Assertions.assertEquals(newName, product.getName());
        Assertions.assertEquals(newPrice, product.getPrice());
    }

    @Test
    public void findShouldReturnOptionalProductNonEmptyWhenIdExists() {
        // Assert
        Long idExists = (long) countTotalProducts - 2;

        // Act
        Optional<Product> result = productRepository.findById(idExists);

        // Assertions
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void findShouldReturnOptionalProductEmptyWhenIdDoesNotExists() {
        // Assert
        Long idExists = (long) countTotalProducts + 2;

        // Act
        Optional<Product> result = productRepository.findById(idExists);

        // Assertions
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void findAllShouldFindAllProductsWithPageableWhenExistsPageable() {
        // Assert
        double totalPages = (double) countTotalProducts / pageable.getPageSize();
        int totalPagesRound = (int) Math.ceil(totalPages);

        // Act
        Page<Product> result = productRepository.findAll(pageable);

        // Assertions
        Assertions.assertEquals(totalPagesRound, result.getTotalPages());
    }
}
