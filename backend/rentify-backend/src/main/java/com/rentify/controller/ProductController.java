package com.rentify.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentify.dto.ProductRequest;
import com.rentify.model.Product;
import com.rentify.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Product> addProduct(
            @RequestPart("product") String productRequestJson,
            @RequestPart("images") List<MultipartFile> images) {

        ObjectMapper objectMapper = new ObjectMapper();
        ProductRequest productRequest;
        try {
            productRequest = objectMapper.readValue(productRequestJson, ProductRequest.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }

        productRequest.setImages(images);
        Product savedProduct = productService.addProduct(productRequest);

        return ResponseEntity.ok(savedProduct);
    }

    //  Fetch all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    //  Fetch a product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
