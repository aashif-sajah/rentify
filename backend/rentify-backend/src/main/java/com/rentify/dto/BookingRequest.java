package com.rentify.dto;

import lombok.Data;

@Data
public class BookingRequest {
    private Long productId;
    private int daysBooked;
    private String address;
    private boolean booked;
}

