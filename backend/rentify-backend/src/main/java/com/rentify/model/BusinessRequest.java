package com.rentify.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessRequest {
    private String businessName;
    private String businessType;
    private String description;
    private String contactEmail;
    private String phone;
    private StoreTheme storeTheme;
}
