package com.rentify.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Booking
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;  // The product being rented

    @Column(nullable = false)
    private LocalDate startDate;  // When booking starts

    @Column(nullable = false)
    private LocalDate endDate;  // When booking ends

    @Column(nullable = false)
    private int daysBooked;  // Number of days

    @Column(nullable = false)
    private double totalAmount;  // Total price (calculated as daysBooked * pricePerDay)

//    @Enumerated(EnumType.STRING)
//    private BookingStatus status = BookingStatus.PENDING;  // PENDING, PAID, CANCELLED
}
