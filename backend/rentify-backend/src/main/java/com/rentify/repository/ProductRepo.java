package com.rentify.repository;

import com.rentify.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {
  List<Product> findByBusinessId(Long businessId);
}
