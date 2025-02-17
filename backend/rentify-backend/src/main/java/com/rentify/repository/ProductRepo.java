package com.rentify.repository;

import com.rentify.model.Business;
import com.rentify.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long>
{
    List<Product> findByBusinessId(Long businessId);

    // ✅ Fetch all products for a specific business
    List<Product> findByBusiness(Business business);

    // ✅ Fetch a single product by business ID and product ID
    Optional<Product> findByIdAndBusiness(Long id, Business business);
}
