package com.rentify.controller;

import com.rentify.model.Business;
import com.rentify.service.BusinessService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/business")
public class BusinessController
{
    private final BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('OWNER')")
    public Business createBusiness(@RequestBody Business business) {
        return businessService.createBusiness(business);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('OWNER')")
    public Business getBusiness(@PathVariable Long userId) {
        return businessService.getBusinessByOwner(userId)
                .orElseThrow(() -> new RuntimeException("Business not found"));
    }
}
