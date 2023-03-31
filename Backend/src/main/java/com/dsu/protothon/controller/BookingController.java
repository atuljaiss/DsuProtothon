package com.codewarrior.travenjo.controller;

import com.codewarrior.travenjo.model.Booking;
import com.codewarrior.travenjo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping(value="/booking", produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Booking> getBookingRequests(@RequestParam String status) {
        return bookingService.getBookings(status);
    }
}
