package com.rentify.service;

import com.rentify.dto.BusinessResponse;
import com.rentify.model.Business;
import com.rentify.model.StoreTheme;
import com.rentify.model.Users;
import com.rentify.repository.BusinessRepo;
import com.rentify.repository.StoreThemeRepo;
import com.rentify.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BusinessServiceTest {

    @Mock
    private BusinessRepo businessRepo;

    @Mock
    private StoreThemeRepo storeThemeRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private CloudinaryService cloudinaryService;

    @InjectMocks
    private BusinessService businessService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBusinessByOwner_Success() {
        // Arrange
        Long userId = 1L;

        Users mockUser = new Users();
        mockUser.setUserId(userId);
        mockUser.setUserEmail("testuser@gmail.com");

        Business mockBusiness = new Business();
        mockBusiness.setId(1L);
        mockBusiness.setBusinessName("Rentify Store");
        mockBusiness.setOwner(mockUser);
        mockBusiness.setStoreSlug("rentify-store");
        mockBusiness.setStoreTheme(new StoreTheme()); // avoid null pointer

        when(userRepo.findById(userId)).thenReturn(Optional.of(mockUser));
        when(businessRepo.findByOwner(mockUser)).thenReturn(Optional.of(mockBusiness));

        // Act
        Optional<BusinessResponse> response = businessService.getBusinessByOwner(userId);

        // Assert
        assertTrue(response.isPresent());
        assertEquals("Rentify Store", response.get().getBusinessName());
        assertEquals("rentify-store", response.get().getStoreSlug());
    }
}
