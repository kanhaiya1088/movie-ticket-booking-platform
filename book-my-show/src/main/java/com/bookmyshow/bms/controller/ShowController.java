package com.bookmyshow.bms.controller;

import com.bookmyshow.bms.dto.ShowDto;
import com.bookmyshow.bms.service.ShowUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("bms")
public class ShowController {

    @Autowired
    private ShowUseCase showUseCase;

    @PostMapping(value = "/show", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ShowDto createShow(@RequestBody final ShowDto showDto){
        log.info("------- Show Added Successfully---------");
        return showUseCase.createShow(showDto);
    }

    @GetMapping(value = "/show/{showId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ShowDto getShowDto(@PathVariable final String showId){
        log.info("------- Show Fetched Successfully---------");
        return showUseCase.getShowDto(showId);
    }

    @GetMapping(value = "/shows-by-movie/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShowDto> getShowsByMovieId(@PathVariable final String movieId){
        log.info("------- Shows by movieId Fetched Successfully---------");
        return showUseCase.getShowsByMovieId(movieId);
    }

    @PutMapping(value = "/show/{showId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ShowDto updateShow(@RequestBody final ShowDto showDto, @PathVariable final String showId){
        log.info("------- Show Updated Successfully---------");
        return showUseCase.updateShow(showDto, showId);
    }

    @DeleteMapping(value = "/show/{showId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ShowDto deleteShow(@PathVariable final String showId){
        log.info("------- Show Deleted Successfully---------");
        return showUseCase.deleteShow(showId);
    }
}
