package com.rentify.controller;

import com.rentify.model.Business;
import com.rentify.model.BusinessRequest;
import com.rentify.model.BusinessResponse;
import com.rentify.model.StoreTheme;
import com.rentify.service.BusinessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/business")
public class BusinessController {
  private final BusinessService businessService;

  public BusinessController(BusinessService businessService) {
    this.businessService = businessService;
  }

  @PostMapping("/create")
  @PreAuthorize("hasRole('Owner')")
  public ResponseEntity<BusinessResponse> createBusiness(
          @RequestBody BusinessRequest businessRequest) {
    BusinessResponse response = businessService.createBusiness(businessRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/{userId}")
  @PreAuthorize("hasRole('Owner')")
  public ResponseEntity<BusinessResponse> getBusiness(@PathVariable Long userId) {
    return businessService
        .getBusinessByOwner(userId)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new RuntimeException("Business not found for user ID: " + userId));
  }
}
