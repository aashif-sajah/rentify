package com.rentify.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long bookingId;
    private String stripeToken;  // This will be sent from the frontend
}

