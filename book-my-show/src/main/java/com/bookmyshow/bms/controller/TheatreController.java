package com.bookmyshow.bms.controller;

import com.bookmyshow.bms.dto.TheatreDto;
import com.bookmyshow.bms.dto.TheatreShowDto;
import com.bookmyshow.bms.service.TheatreUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("bms")
public class TheatreController {

    @Autowired
    private TheatreUseCase theatreUseCase;

    @PostMapping(value = "/theatre", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TheatreDto createTheater(@RequestBody final TheatreDto theatreDto){
        log.info("------- Theatre Added Successfully---------");
        return theatreUseCase.createTheatre(theatreDto);
    }

    @GetMapping(value = "/theatre/{theatreId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TheatreDto getTheatre(@PathVariable final String theatreId){
        log.info("------- Theatre Fetched Successfully---------");
        return theatreUseCase.getTheatre(theatreId);
    }

    @PutMapping(value = "/theatre/{theatreId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TheatreDto updateTheater(@RequestBody final TheatreDto theatreDto, @PathVariable final String theatreId){
        log.info("------- Theatre Updated Successfully---------");
        return theatreUseCase.updateTheatre(theatreDto, theatreId);
    }

    @GetMapping(value = "/theatre-by-city/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TheatreDto> findAllTheatresByCity(@PathVariable final String city){
        log.info("------- Find Theatres By movieId and city ---------");
        return theatreUseCase.findAllTheatresByCity(city);
    }

    /**
     * Browse theatres currently running the show (movie selected) in the town, including show timing by a chosen date
     */
    @GetMapping(value = "/theatre-shows/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TheatreShowDto> findShowsOfTheatresByMovieIdAndCity(@PathVariable final String movieId, @RequestParam(name = "city") final String city,
                                                                    @RequestParam(value = "date", required=false) final String date){
        log.info("------- Find Theatres By movieId and city ---------");
        return theatreUseCase.findShowsOfTheatresByMovieIdAndCity(movieId, city, date);
    }




}
