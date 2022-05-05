package com.bookmyshow.bms.controller;

import com.bookmyshow.bms.dto.SeatDto;
import com.bookmyshow.bms.service.SeatUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("bms")
public class SeatController {

    @Autowired
    private SeatUseCase seatUseCase;

    @PostMapping(value = "/seats/{screenId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<SeatDto> createSeats(@RequestBody final List<SeatDto> seats, @PathVariable final String screenId){
        log.info("------- Seats Added Successfully for Given ScreenId ---------");
        return seatUseCase.saveSeatsForScreen(seats, screenId);
    }

    @PutMapping(value = "/seats/{screenId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SeatDto> updateSeats(@RequestBody final List<SeatDto> seats, @PathVariable final String screenId){
        log.info("------- Seats Updated Successfully for Given ScreenId ---------");
        return seatUseCase.saveSeatsForScreen(seats, screenId);
    }

    @GetMapping(value = "/seats/{screenId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SeatDto> getSeats(@PathVariable final String screenId){
        log.info("------- Seats Fetched Successfully for Given ScreenId ---------");
        return seatUseCase.getSeatDtoByScreenId(screenId);
    }
}
