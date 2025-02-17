package com.rentify.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ProductRequest {
    private String name;
    private String description;
    private Double pricePerDay;
    private Boolean availability;
    private String category;
    private Long businessId;
    private List<MultipartFile> images;
}
