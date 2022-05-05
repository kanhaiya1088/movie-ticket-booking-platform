package com.bookmyshow.bms.controller;

import com.bookmyshow.bms.dto.BookingDto;
import com.bookmyshow.bms.service.BookingUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("bms")
public class BookingController {

    @Autowired
    private BookingUseCase bookingUseCase;

    @PostMapping(value = "/booking", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookingDto createBooking(@RequestBody final BookingDto bookingDto, @RequestParam(value = "userName", required = false) String userName){
        log.info("------- Booking Creation Start---------");
        return bookingUseCase.createBooking(bookingDto, userName);
    }

    @GetMapping(value = "/booking/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookingDto viewBooking(@PathVariable final String bookingId){
        log.info("------- Booking Fetched Successfully---------");
        return bookingUseCase.getBooking(bookingId);
    }

    @GetMapping(value = "/cancel-booking/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookingDto cancelBooking(@PathVariable final String bookingId){
        log.info("------- Booking Cancelled Successfully---------");
        return bookingUseCase.cancelBooking(bookingId);
    }
}
