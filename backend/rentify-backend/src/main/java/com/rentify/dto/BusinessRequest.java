package com.rentify.dto;

import com.rentify.model.StoreTheme;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessRequest {
    private String businessName;
    private String businessType;
    private String address;
    private String description;
    private String contactEmail;
    private String phone;
    private StoreTheme storeTheme;
}
