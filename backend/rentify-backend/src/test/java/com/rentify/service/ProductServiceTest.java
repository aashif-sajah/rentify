package com.rentify.service;

import com.rentify.model.Business;
import com.rentify.model.Product;
import com.rentify.repository.BusinessRepo;
import com.rentify.repository.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepo productRepository;

    @Mock
    private BusinessRepo businessRepository;

    @Mock
    private CloudinaryService cloudinaryService;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProductById_Success() {
        // Arrange
        Long productId = 101L;

        Business business = new Business();
        business.setId(1L);

        Product product = new Product();
        product.setId(productId);
        product.setName("Gaming Chair");
        product.setBusiness(business);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        Optional<Product> result = productService.getProductById(productId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Gaming Chair", result.get().getName());
        assertEquals(1L, result.get().getBusiness().getId());
    }
}
