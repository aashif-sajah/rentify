package com.rentify.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentify.dto.ProductRequest;
import com.rentify.dto.ProductResponse;
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
    public ResponseEntity<ProductResponse> addProduct(
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
        ProductResponse savedProduct = productService.addProduct(productRequest);

        return ResponseEntity.ok(savedProduct);
    }


    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<ProductResponse>> getAllProductsByBusiness(@PathVariable Long businessId) { // we have to return product response
        List<ProductResponse> products = productService.getAllProductsByBusiness(businessId);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductResponse> getProductById( // return product response not product
            @PathVariable Long productId) {
        Optional<Product> product = productService.getProductById(productId);
        return product.map(p -> ResponseEntity.ok(productService.convertToProductResponse(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") String productRequestJson,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {

        ObjectMapper objectMapper = new ObjectMapper();
        ProductRequest productRequest;
        try {
            productRequest = objectMapper.readValue(productRequestJson, ProductRequest.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }

        // Set the images only if they are provided
        if (images != null && !images.isEmpty()) {
            productRequest.setImages(images);
        }

        ProductResponse updatedProduct = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    System.out.println("This line getting called 72 from delete product");
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
