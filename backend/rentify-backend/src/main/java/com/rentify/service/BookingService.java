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
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookingService
{
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final BookingRepo bookingRepo;

    public BookingService(UserRepo userRepo, ProductRepo productRepo, BookingRepo bookingRepo) {
        this.userRepo = userRepo;
        this.productRepo = productRepo;
        this.bookingRepo = bookingRepo;
    }


    public BookingResponse createBooking(BookingRequest bookingRequest, Long userId)
    {
        Users user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepo.findById(bookingRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        double totalAmount = product.getPricePerDay() * bookingRequest.getDaysBooked();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(bookingRequest.getDaysBooked());

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setProduct(product);
        booking.setDaysBooked(bookingRequest.getDaysBooked());
        booking.setTotalAmount(totalAmount);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setStatus(BookingStatus.PENDING);
        
        
        return convertToBookingResponse(bookingRepo.save(booking));
    }

    private BookingResponse convertToBookingResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .userName(booking.getUser().getUserFirstName() + " " + booking.getUser().getUserLastName())
                .userEmail(booking.getUser().getUserEmail())
                .productName(booking.getProduct().getName())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .daysBooked(booking.getDaysBooked())
                .totalAmount(booking.getTotalAmount())
                .status(booking.getStatus())
                .build();
    }
}
