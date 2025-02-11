package com.rentify.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BusinessResponse {
    private Long id;
    private String businessName;
    private String businessType;
    private String description;
    private String contactEmail;
    private String phone;
    private String storeSlug;
    private StoreThemeResponse storeTheme;
    private List<ProductResponse> products;
}
