package com.rentify.config;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.*;

@Configuration
public class CloudinaryConfig {
  @Bean
  public Cloudinary getCloudinary() {
    return new Cloudinary(
        ObjectUtils.asMap(
            "cloud_name", "djafdtwar",
            "api_key", "669113794948366",
            "api_secret", "pMoK95h8zN8yKVTbqJOwcdFhc6A",
            "secure", true));
  }
}
