package com.rentify.controller;

import com.rentify.dto.PaymentRequest;
import com.rentify.model.Payment;
import com.rentify.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-intent/{bookingId}")
    public ResponseEntity<String> createPaymentIntent(@PathVariable Long bookingId) {
        String clientSecret = paymentService.createPaymentIntent(bookingId);
        return ResponseEntity.ok(clientSecret);
    }

    @PostMapping("/confirm/{bookingId}")
    public ResponseEntity<Payment> confirmPayment(@RequestParam String paymentIntentId, @PathVariable Long bookingId) {
        Payment payment = paymentService.confirmPayment(paymentIntentId, bookingId);
        return ResponseEntity.ok(payment);
    }
}

