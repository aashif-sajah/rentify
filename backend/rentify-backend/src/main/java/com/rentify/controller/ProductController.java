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
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Product> addProduct(
            @RequestPart("product") String productRequestJson,
            @RequestPart("images") List<MultipartFile> images) {

    System.out.println(productRequestJson + "line 29 product controller");

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

    // ✅ Fetch all products for a specific business
    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<Product>> getAllProductsByBusiness(@PathVariable Long businessId) { // we have to return product response
        List<Product> products = productService.getAllProductsByBusiness(businessId);
        return ResponseEntity.ok(products);
    }

     // Fetch a single product by businessId and productId
    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProductById( // return product response not product
            @PathVariable Long productId) {
        Optional<Product> product = productService.getProductById(productId);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
