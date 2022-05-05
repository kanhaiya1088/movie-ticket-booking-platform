package com.bookmyshow.bms.service;

import com.bookmyshow.bms.dto.ScreenDto;
import com.bookmyshow.bms.dto.ShowDto;
import com.bookmyshow.bms.dto.TheatreDto;
import com.bookmyshow.bms.dto.TheatreShowDto;
import com.bookmyshow.bms.entity.Theatre;
import com.bookmyshow.bms.exceptions.NotFoundException;
import com.bookmyshow.bms.repository.TheatreRepository;
import com.bookmyshow.bms.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TheatreUseCase {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private ScreenUseCase screenUseCase;

    @Autowired
    private SeatUseCase seatUseCase;

    @Autowired
    private ShowUseCase showUseCase;

    @Autowired
    private MovieUseCase movieUseCase;


    public TheatreDto getTheatre(final String theatreId) {
        Theatre theatreDB = findById(theatreId);
        if(theatreDB == null){
            throw new NotFoundException("Theatre not found for this theatreId : "+ theatreId);
        }
        List<ScreenDto> screenDtos = screenUseCase.getAllScreenByTheatreId(theatreId);
        TheatreDto theatreDto = TheatreDto.builder().id(theatreDB.getId()).name(theatreDB.getName()).city(theatreDB.getCity()).screens(screenDtos).build();
        return theatreDto;
    }

    @Transactional
    public TheatreDto createTheatre(final TheatreDto theatreDto) {
        Theatre theatreObj = modelMapper.map(theatreDto, Theatre.class);
        Theatre newTheatre = theatreRepository.save(theatreObj);
        theatreDto.setId(newTheatre.getId());
        List<ScreenDto> screenDtos = theatreDto.getScreens().stream().map(screenDto->processScreenDto(screenDto, newTheatre.getId())).collect(Collectors.toList());
        theatreDto.setScreens(screenDtos);
        return theatreDto;
    }

    @Transactional
    public TheatreDto updateTheatre(final TheatreDto theatreDto, final String theatreId) {
        Theatre theatreDB = findById(theatreId);
        if(theatreDB == null){
            throw new NotFoundException("Theatre not found for this theatreId : "+ theatreId);
        }
        TheatreDto updatedTheatreDto = modelMapper.map(theatreDto, TheatreDto.class);

        List<ScreenDto> screenDtosDB = screenUseCase.getAllScreenByTheatreId(theatreId);
        List<ScreenDto> updatedScreens = theatreDto.getScreens().stream().map(screen-> mapAndProcessScreenDto(screenDtosDB, screen, theatreId)).collect(Collectors.toList());
        updatedTheatreDto.setScreens(updatedScreens);

        theatreDB.setName(updatedTheatreDto.getName());
        theatreDB.setCity(updatedTheatreDto.getCity());
        theatreRepository.save(theatreDB);
        return updatedTheatreDto;
    }

    private ScreenDto processScreenDto(final ScreenDto screenDto, final String theatreId) {
        screenDto.setTheatreId(theatreId);
        return screenUseCase.saveScreen(screenDto);
    }

    public Theatre findById(final String theatreId){
        return theatreRepository.findById(theatreId).orElse(null);
    }
    
    private List<TheatreDto> findTheatresByCity(final String city){
        List<Theatre> theatresByCity = theatreRepository.findByCity(city);
        return theatresByCity.stream().map(theatre -> getTheatre(theatre.getId())).collect(Collectors.toList());
    }

    private ScreenDto mapAndProcessScreenDto(final List<ScreenDto> existingScreens, final ScreenDto newScreen, final String theatreId) {
        final Optional<ScreenDto> foundScreenDto = existingScreens.stream().filter(existingScreen -> existingScreen.getId().equals(newScreen.getId())).findFirst();
        if(foundScreenDto.isPresent()){
            ScreenDto existingScreenDto = foundScreenDto.get();
            existingScreenDto.setName(newScreen.getName());
            existingScreenDto.setTheatreId(theatreId);
            existingScreenDto.setSeats(newScreen.getSeats());
            existingScreenDto.setTheatreId(theatreId);
            return screenUseCase.updateScreen(existingScreenDto, newScreen.getId());
        }
        return processScreenDto(newScreen, theatreId);
    }

    public List<TheatreDto> findAllTheatresByCity(final String city) {
        List<TheatreDto> theatresByCity = findTheatresByCity(city);
        return theatresByCity;
    }

    public List<TheatreShowDto> findShowsOfTheatresByMovieIdAndCity(final String movieId, final String city, final String dateStr) {
        Date date = StringUtils.isBlank(dateStr) ? new Date() : DateUtils.convertStringToDate(dateStr, DateUtils.DATE_HH_MM_SS_PATTERN);
        List<ShowDto> showsByMovieId = showUseCase.getShowsByMovieIdAndDate(movieId, date);
        List<TheatreDto> theatresByCity = findTheatresByCity(city);

        List<TheatreShowDto> theatreList = theatresByCity.stream().map(theatreDto -> mapTheatresWithShows(showsByMovieId, theatreDto)).collect(Collectors.toList());
        return theatreList;
    }

    private TheatreShowDto mapTheatresWithShows(final List<ShowDto> showsByMovieId, final TheatreDto theatreDto) {
        TheatreShowDto theatreShowDto = modelMapper.map(theatreDto, TheatreShowDto.class);
        List<ShowDto> showDtos = showsByMovieId.stream().filter(showDto -> showDto.getTheatreId().equals(theatreDto.getId())).collect(Collectors.toList());
        theatreShowDto.setShows(showDtos);
        return theatreShowDto;
    }
}
