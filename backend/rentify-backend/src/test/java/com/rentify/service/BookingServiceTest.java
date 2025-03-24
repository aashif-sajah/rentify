package com.rentify.service;

import com.rentify.dto.BookingRequest;
import com.rentify.dto.BookingResponse;
import com.rentify.model.Booking;
import com.rentify.model.Product;
import com.rentify.model.Users;
import com.rentify.repository.BookingRepo;
import com.rentify.repository.ProductRepo;
import com.rentify.repository.UserRepo;
import com.rentify.util.BookingStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private ProductRepo productRepo;

    @Mock
    private BookingRepo bookingRepo;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBooking_Success() {
        // Arrange
        Long userId = 1L;
        Long productId = 2L;

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setProductId(productId);
        bookingRequest.setDaysBooked(3);

        Users mockUser = new Users();
        mockUser.setUserId(userId);
        mockUser.setUserFirstName("John");
        mockUser.setUserLastName("Doe");
        mockUser.setUserEmail("john@example.com");

        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setName("Test Product");
        mockProduct.setPricePerDay(100.0);

        Booking mockBooking = new Booking();
        mockBooking.setId(99L);
        mockBooking.setUser(mockUser);
        mockBooking.setProduct(mockProduct);
        mockBooking.setDaysBooked(3);
        mockBooking.setTotalAmount(300.0);
        mockBooking.setStartDate(LocalDate.now());
        mockBooking.setEndDate(LocalDate.now().plusDays(3));
        mockBooking.setStatus(BookingStatus.PENDING);

        when(userRepo.findById(userId)).thenReturn(Optional.of(mockUser));
        when(productRepo.findById(productId)).thenReturn(Optional.of(mockProduct));
        when(bookingRepo.save(any(Booking.class))).thenReturn(mockBooking);

        // Act
        BookingResponse response = bookingService.createBooking(bookingRequest, userId);

        // Assert
        assertNotNull(response);
        assertEquals("John Doe", response.getUserName());
        assertEquals("Test Product", response.getProductName());
        assertEquals(300.0, response.getTotalAmount());
    }
}
