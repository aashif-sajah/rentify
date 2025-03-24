package com.rentify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StoreThemeResponse {
    private String fontStyle;
    private String primaryColor;
    private String logoUrl;
}
