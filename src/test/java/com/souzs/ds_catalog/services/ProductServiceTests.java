package com.souzs.ds_catalog.services;

import com.souzs.ds_catalog.domain.dto.ProductDTO;
import com.souzs.ds_catalog.domain.entities.Category;
import com.souzs.ds_catalog.domain.entities.Product;
import com.souzs.ds_catalog.repository.CategoryRepository;
import com.souzs.ds_catalog.repository.ProductRepository;
import com.souzs.ds_catalog.services.exceptions.DatabaseException;
import com.souzs.ds_catalog.services.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
    @InjectMocks
    private ProductService productService;

    // Teste de unidade nao tem acesso ao productRepository real.
    // Por isso precisamos mockar ele e simular seu comportamento, de seus metodos.
    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private PageImpl<Product> page;
    private Product product;
    private Category category;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        dependentId = 2L;
        nonExistingId = 10000L;
        product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png");
        category = new Category(1L, "Livros");
        page = new PageImpl<>(List.of(product));

        // Configura o comportamento simulado do metodo existsById
        Mockito.when(productRepository.existsById(existingId)).thenReturn(true);
        Mockito.when(productRepository.existsById(nonExistingId)).thenReturn(false);
        Mockito.when(productRepository.existsById(dependentId)).thenReturn(true);

        // Configura o comportamento simulado do metodo deleteById
        Mockito.doNothing().when(productRepository).deleteById(existingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependentId);

        // Configura o comportamento simulado do metodo findAll recebendo um Pageable
        // ArgumentMatchers retorna um argumento qualquer. Nesse caso o cast precisou ser feito
        // pois o metodo findAll tem sobrecarga,
        Mockito.when(productRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        // Configura o comportamento simulado do findById
        Mockito.when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.findById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        // Configura o comportamento simulado do metodo save
        Mockito.when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);

        // Configura o comportamento simulado do metodo getReferenceById para productRepository
        Mockito.when(productRepository.getReferenceById(existingId)).thenReturn(product);
        Mockito.when(productRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        // Configura o comportamento simulado do metodo getReferenceById para categoryRepository
        Mockito.when(categoryRepository.getReferenceById(existingId)).thenReturn(category);
        Mockito.when(categoryRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
    }

    @Test
    public void updateShouldThrowEntityNotFoundExceptionWhenIdDoesNotExist() {
        // Arrange
        ProductDTO dto = new ProductDTO(product);

        // Assertions
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            // Act
            ProductDTO result = productService.update(nonExistingId, dto);
        });
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() {
        // Arrange
        ProductDTO dto = new ProductDTO(product);

        // Act
        ProductDTO result = productService.update(existingId, dto);

        // Assertions
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.id(), dto.id());
        Mockito.verify(productRepository, Mockito.times(1)).save(product);
    }

    @Test
    public void findByIdShouldReturnProductDTOWhenIdExists() {
        // Act
        ProductDTO result = productService.searchById(existingId);

        // Assertions
        Assertions.assertNotNull(result);
        Mockito.verify(productRepository, Mockito.times(1)).findById(existingId);
    }

    @Test
    public void findByIdShouldThrowEntityNotFoundExceptionWhenIdDoesNotExist() {
        // Act
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            productService.searchById(nonExistingId);
        });

        // Assertions
        Mockito.verify(productRepository, Mockito.times(1)).findById(nonExistingId);
    }

    @Test
    public void findAllPagedShouldReturnPage() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<ProductDTO> result = productService.searchAll(pageable);

        // Assertions
        Assertions.assertNotNull(result);
        // Verifica se o metodo findAll do productRepository foi chamado 1 vez.
        Mockito.verify(productRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    public void deleteShouldThrowDataIntegrityViolationExceptionWhenDependentId() {
        Assertions.assertThrows(DatabaseException.class, () -> {
            productService.delete(dependentId);
        });
    }

    public void deleteShouldThrowEntityNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            productService.delete(nonExistingId);
        });
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        // Nenhuma excecao deve ser lancada
        Assertions.assertDoesNotThrow(() -> {
            productService.delete(existingId);
        });
    }
}
