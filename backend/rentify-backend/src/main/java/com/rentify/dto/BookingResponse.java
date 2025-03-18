package com.rentify.dto;

import com.rentify.util.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse
{
    private long id;
    private String userName;
    private String userEmail;
    private String productName;
    private LocalDate startDate;
    private LocalDate endDate;
    private int daysBooked;
    private double totalAmount;
    private BookingStatus status;
}
