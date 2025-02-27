package com.rentify.service;

import com.rentify.dto.ProductRequest;
import com.rentify.dto.ProductResponse;
import com.rentify.model.Business;
import com.rentify.model.Product;
import com.rentify.repository.BusinessRepo;
import com.rentify.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepository;
    private final BusinessRepo businessRepository;
    private final CloudinaryService cloudinaryService;

    public ProductResponse addProduct(ProductRequest request) {
    // Validate business
        System.out.println("Product Service line 26 Business ID: " + request.getBusinessId());
        Business business = businessRepository.findById(request.getBusinessId())
                .orElseThrow(() -> new RuntimeException("Business not found"));

        // Upload images to Cloudinary
        List<String> imageUrls = cloudinaryService.uploadImages(request.getImages());

        // Create product
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPricePerDay(request.getPricePerDay());
        product.setAvailability(request.getAvailability());
        product.setCategory(request.getCategory());
        product.setImageUrls(imageUrls);
        product.setBusiness(business);

        Product savedProduct =  productRepository.save(product);
        return convertToProductResponse(savedProduct);
    }

    public ProductResponse convertToProductResponse(Product savedProduct) {
        return ProductResponse.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .category(savedProduct.getCategory())
                .description(savedProduct.getDescription())
                .availability(savedProduct.getAvailability())
                .pricePerDay(savedProduct.getPricePerDay())
                .imageUrls(savedProduct.getImageUrls())
                .businessId(savedProduct.getBusiness().getId())
                .build();
    }



    public Optional<Product> getProductById(Long id)
    {
        return productRepository.findById(id);
    }

    public List<ProductResponse> getAllProductsByBusiness(Long businessId) {
        List<Product> products = productRepository.findByBusinessId(businessId);
        return products.stream().map(this::convertToProductResponse).toList();
    }


    public ProductResponse updateProduct(Long id, ProductRequest request) {
    Product product =
        productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPricePerDay(request.getPricePerDay());
        product.setAvailability(request.getAvailability());
        product.setCategory(request.getCategory());

        // Handle images
//        if (request.getImages() != null && !request.getImages().isEmpty()) {
//            List<String> imageUrls = fileStorageService.storeFiles(request.getImages());
//            product.setImageUrls(imageUrls);
//        }

        Product updatedProduct = productRepository.save(product);
        return convertToProductResponse(updatedProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
      throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

}
