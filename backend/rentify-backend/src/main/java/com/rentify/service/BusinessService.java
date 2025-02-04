package com.rentify.service;

import com.rentify.model.Business;
import com.rentify.model.Users;
import com.rentify.repository.BusinessRepo;
import com.rentify.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BusinessService {
  private final BusinessRepo businessRepo;

  private final UserRepo userRepo;

  public Business createBusiness(Business business) {
    String userEmail = SecurityContextHolder.getContext().getAuthentication().getName() + "@gmail.com";
    Users owner =
        userRepo
            .findByUserEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (businessRepo.findByOwner(owner).isPresent()) {
      throw new RuntimeException("User already has a business");
    }

    business.setOwner(owner);
    business.setStoreSlug(generateSlug(business.getBusinessName()));
    return businessRepo.save(business);
  }

  public Optional<Business> getBusinessByOwner(Long userId) {
    Users owner =
        userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    return businessRepo.findByOwner(owner);
  }

  private String generateSlug(String businessName) {
    return businessName.toLowerCase().replaceAll("\\s+", "-");
  }
}
