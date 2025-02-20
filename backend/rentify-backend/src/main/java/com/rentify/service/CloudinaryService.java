package com.rentify.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class CloudinaryService
{
    private static final Logger logger = Logger.getLogger(CloudinaryService.class.getName());
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("secure_url").toString(); // Always return secure URL
        } catch (IOException e) {
            logger.severe("Image upload failed: " + e.getMessage());
            throw new RuntimeException("Image upload failed", e);
        }
    }

    public List<String> uploadImages(List<MultipartFile> images) {
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile image : images) {
            try {
                Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
                imageUrls.add(uploadResult.get("secure_url").toString());
            } catch (IOException e) {
                logger.warning("⚠️ Failed to upload image: " + e.getMessage() + " - " + e.getMessage());
                // Continue uploading other images instead of failing the whole request
            }
        }

        if (imageUrls.isEmpty()) {
            throw new RuntimeException("No images could be uploaded!");
        }

        return imageUrls;
    }


}
