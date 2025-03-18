package com.rentify.controller;

import com.rentify.dto.BookingRequest;
import com.rentify.model.Booking;
import com.rentify.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController
{
    private final BookingService bookingService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest request, @PathVariable Long userId) {
        Booking booking = bookingService.createBooking(request, userId);
        return ResponseEntity.ok(booking);
    }
}
