package com.rentify.service;

import com.rentify.config.CloudinaryConfig;
import com.rentify.dto.ProductRequest;
import com.rentify.model.Business;
import com.rentify.model.Product;
import com.rentify.repository.BusinessRepo;
import com.rentify.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepository;
    private final BusinessRepo businessRepository;
    private final CloudinaryConfig cloudinaryService;

    @Transactional
    public Product addProduct(ProductRequest request) {
        // Validate business
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

        return productRepository.save(product);
    }


    public Optional<Product> getProductById(Long id)
    {
        return productRepository.findById(id);
    }

    public List<Product> getAllProductsByBusiness(Long businessId) {
        return productRepository.findByBusinessId(businessId);
    }
}
