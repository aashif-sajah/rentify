package com.rentify.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double pricePerDay;
    private Boolean availability;
    private String category;
    private List<String> imageUrls;
}
