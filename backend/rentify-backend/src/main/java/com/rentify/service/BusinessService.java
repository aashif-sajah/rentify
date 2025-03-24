package com.rentify.service;


import com.rentify.dto.BusinessRequest;
import com.rentify.dto.BusinessResponse;
import com.rentify.dto.StoreThemeResponse;
import com.rentify.model.*;
import com.rentify.repository.BusinessRepo;
import com.rentify.repository.StoreThemeRepo;
import com.rentify.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.Optional;

@Service
@AllArgsConstructor
public class BusinessService {
  private final BusinessRepo businessRepo;
  private final StoreThemeRepo storeThemeRepo;
  private final CloudinaryService cloudinaryService;

  private final UserRepo userRepo;

  public BusinessResponse createBusiness(BusinessRequest request, MultipartFile image) {

    String userEmail = SecurityContextHolder.getContext().getAuthentication().getName() + "@gmail.com";
    Users owner =
        userRepo
            .findByUserEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (businessRepo.findByOwner(owner).isPresent()) {
      throw new RuntimeException("User already has a business");
    }

    String imageUrl = cloudinaryService.uploadImage(image);

    Business business = new Business();
    business.setBusinessName(request.getBusinessName());
    business.setBusinessType(request.getBusinessType());
    business.setDescription(request.getDescription());
    business.setContactEmail(request.getContactEmail());
    business.setAddress(request.getAddress());
    business.setPhone(request.getPhone());
    business.setOwner(owner);
    business.setStoreSlug(generateSlug(request.getBusinessName()));

    Business savedBusiness = businessRepo.save(business);

    // saving the store details as well
    StoreTheme storeTheme =  request.getStoreTheme();
    storeTheme.setBusiness(savedBusiness);
    storeTheme.setLogoUrl(imageUrl);
    StoreTheme savedStoreTheme = storeThemeRepo.save(storeTheme);

    savedBusiness.setStoreTheme(savedStoreTheme);
    businessRepo.save(savedBusiness);

    return convertToBusinessResponse(savedBusiness);

  }

  public BusinessResponse convertToBusinessResponse(Business business)
  {
    return BusinessResponse.builder()
            .id(business.getId())
            .businessName(business.getBusinessName())
            .businessType(business.getBusinessType())
            .description(business.getDescription())
            .contactEmail(business.getContactEmail())
            .phone(business.getPhone())
            .address(business.getAddress())
            .storeSlug(business.getStoreSlug())
            .storeTheme(new StoreThemeResponse(
                    business.getStoreTheme().getFontStyle(),
                    business.getStoreTheme().getPrimaryColor(),
                    business.getStoreTheme().getLogoUrl()
            ))
            .isProductAvailable(!business.getProducts().isEmpty()) // Check if products exist
            .build();
  }

  public Optional<BusinessResponse> getBusinessByOwner(Long userId) {
    Users owner =
        userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    return businessRepo.findByOwner(owner).map(this::convertToBusinessResponse);
  }

  private String generateSlug(String businessName) {
    return businessName.toLowerCase().replaceAll("\\s+", "-");
  }

  public BusinessResponse updateBusiness(Long businessId, BusinessRequest request, MultipartFile image) {
    Business business = businessRepo.findById(businessId)
            .orElseThrow(() -> new RuntimeException("Business not found"));


    String userEmail = SecurityContextHolder.getContext().getAuthentication().getName() + "@gmail.com";
    Users owner = userRepo.findByUserEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (!business.getOwner().getUserId().equals(owner.getUserId())) {
      throw new RuntimeException("Unauthorized: You can only update your own business");
    }

    // Update business details
    if (request.getBusinessName() != null) {
      business.setBusinessName(request.getBusinessName());
      business.setStoreSlug(generateSlug(request.getBusinessName())); // Update slug
    }
    if (request.getBusinessType() != null) {
      business.setBusinessType(request.getBusinessType());
    }
    if (request.getDescription() != null) {
      business.setDescription(request.getDescription());
    }
    if (request.getContactEmail() != null) {
      business.setContactEmail(request.getContactEmail());
    }
    if (request.getPhone() != null) {
      business.setPhone(request.getPhone());
    }
    if (request.getAddress() != null) {
      business.setAddress(request.getAddress());
    }

    // Update store theme
    if (request.getStoreTheme() != null) {
      StoreTheme storeTheme = business.getStoreTheme();
      storeTheme.setPrimaryColor(request.getStoreTheme().getPrimaryColor());
      storeTheme.setFontStyle(request.getStoreTheme().getFontStyle());
      storeThemeRepo.save(storeTheme);
    }

    // Update logo image if provided
    if (image != null && !image.isEmpty()) {
      String imageUrl = cloudinaryService.uploadImage(image);
      business.getStoreTheme().setLogoUrl(imageUrl);
    }

    Business updatedBusiness = businessRepo.save(business);
    return convertToBusinessResponse(updatedBusiness);
  }


  public BusinessResponse getBusinessByBusinessSlug(String businessSlug)
  {
    return businessRepo.findByStoreSlug(businessSlug).map(this::convertToBusinessResponse).orElse(null);
  }
}
