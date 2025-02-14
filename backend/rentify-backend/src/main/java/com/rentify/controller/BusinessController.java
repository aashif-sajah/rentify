package com.rentify.controller;


import com.rentify.model.BusinessRequest;
import com.rentify.model.BusinessResponse;
import com.rentify.service.BusinessService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/business")
@AllArgsConstructor
public class BusinessController {
  private final BusinessService businessService;
//  private final CloudinaryConfig cloudinaryConfig;



  @PostMapping("/create")
  @PreAuthorize("hasRole('Owner')")
  public ResponseEntity<BusinessResponse> createBusiness(
          @RequestPart("businessRequest") BusinessRequest businessRequest,
          @RequestPart("image") MultipartFile image) {
    BusinessResponse response = businessService.createBusiness(businessRequest,image);
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

//  @PostMapping("/test")
//  public String storeImage(@RequestBody MultipartFile image)
//  {
//      return cloudinaryConfig.uploadImage(image);
//  }
}
