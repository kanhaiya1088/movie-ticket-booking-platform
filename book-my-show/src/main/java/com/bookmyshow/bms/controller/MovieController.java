package com.bookmyshow.bms.controller;

import com.bookmyshow.bms.dto.MovieDto;
import com.bookmyshow.bms.service.MovieUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("bms")
public class MovieController {

    @Autowired
    private MovieUseCase movieUseCase;

    @PostMapping(value = "/movie", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MovieDto createMovie(@RequestBody final MovieDto movieDto){
        log.info("------- Movie Added Successfully---------");
        return movieUseCase.saveMovie(movieDto);
    }

    @GetMapping(value = "/movie/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MovieDto getMovie(@PathVariable final String movieId){
        log.info("------- Movie Fetched Successfully---------");
        return movieUseCase.getMovie(movieId);
    }

    @GetMapping(value = "/movie/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MovieDto> getAllMovie(){
        log.info("------- All Movie Fetched Successfully---------");
        return movieUseCase.getAllMovie();
    }

    @PutMapping(value = "/movie/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MovieDto updateMovie(@RequestBody final MovieDto movieDto, @PathVariable final String movieId){
        log.info("------- Movie Updated Successfully---------");
        return movieUseCase.updateMovie(movieDto, movieId);
    }
}
