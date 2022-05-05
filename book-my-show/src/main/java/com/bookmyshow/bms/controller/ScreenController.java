package com.bookmyshow.bms.controller;

import com.bookmyshow.bms.dto.ScreenDto;
import com.bookmyshow.bms.service.ScreenUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("bms")
public class ScreenController {

    @Autowired
    private ScreenUseCase screenUseCase;

    @PostMapping(value = "/screen", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ScreenDto createScreen(@RequestBody final ScreenDto screenDto){
        log.info("------- Screen Added Successfully---------");
        return screenUseCase.saveScreen(screenDto);
    }

    @GetMapping(value = "/screen/{screenId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScreenDto getMovie(@PathVariable final String screenId){
        log.info("------- Screen Fetched Successfully---------");
        return screenUseCase.getScreen(screenId);
    }

    @GetMapping(value = "/screens-by-theatreId/{theatreId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScreenDto> getAllScreenByTheatreId(@PathVariable final String theatreId){
        log.info("------- All Screen By theatreId Fetched Successfully---------");
        return screenUseCase.getAllScreenByTheatreId(theatreId);
    }

    @PutMapping(value = "/screen/{screenId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ScreenDto updateScreen(@RequestBody final ScreenDto screenDto, @PathVariable final String screenId){
        log.info("------- Screen Updated Successfully---------");
        return screenUseCase.updateScreen(screenDto, screenId);
    }
}
