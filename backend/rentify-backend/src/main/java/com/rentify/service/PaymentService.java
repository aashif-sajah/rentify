package com.rentify.service;

import com.rentify.dto.PaymentRequest;
import com.rentify.model.Booking;
import com.rentify.model.Payment;
import com.rentify.repository.BookingRepo;
import com.rentify.repository.PaymentRepo;
import com.rentify.util.BookingStatus;
import com.rentify.util.PaymentStatus;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {
  private final PaymentRepo paymentRepository;
  private final BookingRepo bookingRepository;
  private final StripeService stripeService;

  public String createPaymentIntent(Long bookingId) {
    Booking booking =
        bookingRepository
            .findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));

    try {
      return stripeService.createPaymentIntent(booking.getTotalAmount(), "usd");
    } catch (StripeException e) {
      throw new RuntimeException("Failed to create payment intent: " + e.getMessage());
    }

  }

  @Transactional
  public Payment confirmPayment(String paymentIntentId, Long bookingId) {
    Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));

    try {
      PaymentIntent paymentIntent = stripeService.retrievePaymentIntent(paymentIntentId);

      if ("succeeded".equals(paymentIntent.getStatus())) {
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setPaymentIntentId(paymentIntentId);
        payment.setAmountPaid(booking.getTotalAmount());
        payment.setCurrency("usd");
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPaymentMethod(paymentIntent.getPaymentMethod());

        booking.setStatus(BookingStatus.PAID);
        bookingRepository.save(booking);

        return paymentRepository.save(payment);
      } else {
        throw new RuntimeException("Payment not successful");
      }
    } catch (StripeException e) {
      throw new RuntimeException("Payment verification failed: " + e.getMessage());
    }
  }
}
