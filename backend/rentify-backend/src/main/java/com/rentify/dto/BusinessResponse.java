package com.rentify.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class BusinessResponse {
    private Long id;
    private String businessName;
    private String businessType;
    private String description;
    private String address;
    private String contactEmail;
    private String phone;
    private String storeSlug;
    private StoreThemeResponse storeTheme;
    private boolean isProductAvailable;
}
