package com.rentify.service;


import com.rentify.dto.PaymentRequest;
import com.rentify.model.Booking;
import com.rentify.model.Payment;
import com.rentify.repository.BookingRepo;
import com.rentify.repository.PaymentRepo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService
{
    private final PaymentRepo paymentRepository;
    private final BookingRepo bookingRepository;
    private final StripeService stripeService;


    public Payment processPayment(PaymentRequest paymentRequest) {
        Booking booking = bookingRepository.findById(paymentRequest.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Stripe.apiKey = "sk_test_YourSecretKey"; // Use environment variables in production

        try {
            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount((long) (booking.getTotalAmount() * 100)) // Convert to cents
                            .setCurrency("usd")
                            .setPaymentMethod(paymentRequest.getStripeToken())
                            .setConfirm(true)
                            .build();

            PaymentIntent intent = PaymentIntent.create(params);

            Payment payment = new Payment();
            payment.setBooking(booking);
            payment.setPaymentIntentId(intent.getId());
            payment.setAmountPaid(booking.getTotalAmount());
            payment.setCurrency("usd");
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setPaymentMethod(intent.getPaymentMethod());

            // Update booking status
            booking.setStatus(BookingStatus.PAID);
            bookingRepository.save(booking);

            return paymentRepository.save(payment);
        } catch (StripeException e) {
            throw new RuntimeException("Payment failed: " + e.getMessage());
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }
}
