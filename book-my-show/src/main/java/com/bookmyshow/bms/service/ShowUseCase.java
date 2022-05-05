package com.bookmyshow.bms.service;

import com.bookmyshow.bms.dto.MovieDto;
import com.bookmyshow.bms.dto.ScreenDto;
import com.bookmyshow.bms.dto.SeatDto;
import com.bookmyshow.bms.dto.ShowDto;
import com.bookmyshow.bms.entity.Show;
import com.bookmyshow.bms.exceptions.NotFoundException;
import com.bookmyshow.bms.repository.ShowRepository;
import lombok.extern.slf4j.Slf4j;
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
public class ShowUseCase {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ScreenUseCase screenUseCase;

    @Autowired
    private MovieUseCase movieUseCase;

    @Autowired
    private SeatUseCase seatUseCase;

    public ShowDto getShowDto(final String showId) {
        Show show = findById(showId);
        if(show == null){
            throw new NotFoundException("Show not found for given showId : "+ showId);
        }
        ShowDto showDto = modelMapper.map(show, ShowDto.class);
        MovieDto movieDto = movieUseCase.getMovie(show.getMovieId());
        ScreenDto screenDto = screenUseCase.getScreen(show.getScreenId());
        showDto.setMovie(movieDto);
        showDto.setScreen(screenDto);
        return showDto;
    }

    @Transactional
    public ShowDto createShow(final ShowDto showDto) {
        Show showData = modelMapper.map(showDto, Show.class);
        MovieDto movieDto = movieUseCase.getMovie(showDto.getMovie().getId());
        ScreenDto screenDto = screenUseCase.getScreen(showDto.getScreen().getId());
        showData.setMovieId(movieDto.getId());
        showData.setScreenId(screenDto.getId());
        Show showDB = showRepository.save(showData);
        ShowDto mappedShowDto = modelMapper.map(showDB, ShowDto.class);
        mappedShowDto.setMovie(movieDto);
        mappedShowDto.setScreen(screenDto);
        return mappedShowDto;
    }

    public List<ShowDto> getShowsByMovieId(final String movieId){
        List<Show> shows = showRepository.findByMovieId(movieId);
        List<ShowDto> showDtoList = shows.stream().map(show -> mapShowDto(show)).collect(Collectors.toList());
        return showDtoList;
    }

    public List<ShowDto> getShowsByMovieIdAndDate(final String movieId, final Date date){
        List<Show> shows = showRepository.findByMovieIdAndDate(movieId, date);
        List<ShowDto> showDtoList = shows.stream().map(show -> mapShowDto(show)).collect(Collectors.toList());
        return showDtoList;
    }

    public List<ShowDto> getShowsByTheatreId(final String theatreId){
        List<Show> shows = showRepository.findByTheatreId(theatreId);
        List<ShowDto> showDtoList = shows.stream().map(show -> mapShowDto(show)).collect(Collectors.toList());
        return showDtoList;
    }

    public List<ShowDto> getShowsByMovieIdAndTheatreId(final String movieId, final String theatreId){
        List<Show> shows = showRepository.findByMovieIdAndTheatreId(movieId, theatreId);
        List<ShowDto> showDtoList = shows.stream().map(show -> mapShowDto(show)).collect(Collectors.toList());
        return showDtoList;
    }

    private ShowDto mapShowDto(final Show show) {
        ShowDto showDto =  modelMapper.map(show, ShowDto.class);
        MovieDto movieDto = movieUseCase.getMovie(show.getMovieId());
        ScreenDto screenDto = screenUseCase.getScreen(show.getScreenId());
        showDto.setMovie(movieDto);
        showDto.setScreen(screenDto);
        return showDto;
    }

    public Show findById(final String showId) {
        return showRepository.findById(showId).orElse(null);
    }

    @Transactional
    public ShowDto updateShow(final ShowDto showDto, final String showId) {
        Show showDB = findById(showId);
        if(showDB == null){
            throw new NotFoundException("Show not found for given showId : "+ showId);
        }
        ShowDto updatedShowDto = modelMapper.map(showDto, ShowDto.class);

        MovieDto requestedMovieDto = movieUseCase.getMovie(showDto.getMovie().getId());
        ScreenDto requestedScreenDto = screenUseCase.getScreen(showDto.getScreen().getId());

        updatedShowDto.setMovie(requestedMovieDto);
        updatedShowDto.setScreen(requestedScreenDto);

        showDB.setName(showDto.getName());
        showDB.setMovieId(requestedMovieDto.getId());
        showDB.setScreenId(requestedScreenDto.getId());
        showDB.setTheatreId(showDto.getTheatreId());
        showDB.setStartTime(showDto.getStartTime());
        showDB.setEndTime(showDto.getEndTime());
        showDB.setDurationInMinutes(showDto.getDurationInMinutes());
        showRepository.save(showDB);
        return updatedShowDto;
    }

    private SeatDto mapAndProcessSeatDto(final List<SeatDto> existingSeats, SeatDto newSeat) {
        final Optional<SeatDto> foundSeatDto = existingSeats.stream().filter(existingSeat -> existingSeat.getId().equals(newSeat.getId())).findFirst();
        if(foundSeatDto.isPresent()){
            SeatDto existingSeatDto = foundSeatDto.get();
            existingSeatDto.setRowNo(newSeat.getRowNo());
            existingSeatDto.setSeatNo(newSeat.getSeatNo());
            existingSeatDto.setPrice(newSeat.getPrice());
            return seatUseCase.saveSeat(existingSeatDto);
        }
        return seatUseCase.saveSeat(newSeat);
    }

    /**
     *  Show Delete by given ShowId
     */
    public ShowDto deleteShow(final String showId) {
        Show show = findById(showId);
        if(show == null){
            throw new NotFoundException("Show not found for given showId : "+ showId);
        }
        ShowDto showDto = modelMapper.map(show, ShowDto.class);
        MovieDto movieDto = movieUseCase.getMovie(show.getMovieId());
        ScreenDto screenDto = screenUseCase.getScreen(show.getScreenId());
        showDto.setMovie(movieDto);
        showDto.setScreen(screenDto);
        showRepository.delete(show);
        return showDto;
    }
}
