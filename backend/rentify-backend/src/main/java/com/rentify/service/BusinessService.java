package com.rentify.service;

import com.rentify.model.Business;
import com.rentify.model.Users;
import com.rentify.repository.BusinessRepo;
import com.rentify.repository.UserRepo;

import java.util.Optional;

public class BusinessService
{
    private final BusinessRepo businessRepo;

    private final UserRepo userRepo;

    public BusinessService(BusinessRepo businessRepo, UserRepo userRepo) {
        this.businessRepo = businessRepo;
        this.userRepo = userRepo;
    }

    public Business createBusiness(Long userId, Business business) {
        Users owner = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if user already has a business
        if (businessRepo.findByOwner(owner).isPresent()) {
            throw new RuntimeException("User already has a business");
        }

        business.setOwner(owner);
        business.setStoreSlug(generateSlug(business.getBusinessName()));
        return businessRepo.save(business);
    }

    public Optional<Business> getBusinessByOwner(Long userId) {
        Users owner = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return businessRepo.findByOwner(owner);
    }

    private String generateSlug(String businessName) {
        return businessName.toLowerCase().replaceAll("\\s+", "-");
    }

}
