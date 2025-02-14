package com.rentify.config;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Value("${cloudinary.cloud_name}")
    private String cloudName;

    @Value("${cloudinary.api_key}")
    private String apiKey;

    @Value("${cloudinary.api_secret}")
    private String apiSecret;


    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", cloudName,
                        "api_key", apiKey,
                        "api_secret", apiSecret,
                        "secure", true
                ));
    }

    public String uploadImage(MultipartFile file) {
        try {
            Map uploadResult = cloudinary().uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            System.out.println(uploadResult.toString());
            return uploadResult.get("url").toString(); // Get the URL of the uploaded image
        } catch (Exception e) {
      System.out.println("exception occured");
            throw new RuntimeException("Image upload failed", e);
        }
    }
}
