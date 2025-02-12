package com.rentify.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Builder
public class BusinessResponse {
    private Long id;
    private String businessName;
    private String businessType;
    private String description;
    private String contactEmail;
    private String phone;
    private String storeSlug;
    private StoreThemeResponse storeTheme;
    private boolean isProductAvailable;
}
