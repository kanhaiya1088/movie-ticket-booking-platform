package com.bookmyshow.bms.service;

import com.bookmyshow.bms.dto.ScreenDto;
import com.bookmyshow.bms.dto.SeatDto;
import com.bookmyshow.bms.entity.Screen;
import com.bookmyshow.bms.exceptions.NotFoundException;
import com.bookmyshow.bms.repository.ScreenRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScreenUseCase {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private SeatUseCase seatUseCase;


    public ScreenDto getScreen(final String screenId) {
        Screen screen = findById(screenId);
        if(screen == null){
            throw new NotFoundException("Screen not found for this screenId : "+ screenId);
        }
        ScreenDto screenDto = modelMapper.map(screen, ScreenDto.class);
        List<SeatDto> seats = seatUseCase.getSeatDtoByScreenId(screenId);
        screenDto.setSeats(seats);
        return screenDto;
    }

    @Transactional
    public ScreenDto saveScreen(final ScreenDto screenDto) {
        Screen screenData = modelMapper.map(screenDto, Screen.class);
        Screen screen = screenRepository.save(screenData);
        List<SeatDto> seats  = screenDto.getSeats().stream().map(seat-> mapAndProcessSeat(seat, screen.getId())).collect(Collectors.toList());
        screenDto.setSeats(seats);
        screenDto.setId(screen.getId());
        return screenDto;
    }

    private SeatDto mapAndProcessSeat(final SeatDto seatDto, final String screenId) {
        seatDto.setScreenId(screenId);
        return seatUseCase.saveSeat(seatDto);
    }

    public Screen findById(final String id) {
        return screenRepository.findById(id).orElse(null);
    }

    public List<ScreenDto> getAllScreenByTheatreId(final String theatreId) {
        List<Screen> screens = screenRepository.findByTheatreId(theatreId);
        List<ScreenDto> screenDtos = screens.stream().map(screen -> mapToScreenDto(screen)).collect(Collectors.toList());
        return screenDtos;
    }

    private ScreenDto mapToScreenDto(final Screen screen) {
        ScreenDto screenDto = modelMapper.map(screen, ScreenDto.class);
        List<SeatDto> seats = seatUseCase.getSeatDtoByScreenId(screen.getId());
        screenDto.setSeats(seats);
        return screenDto;
    }

    @Transactional
    public ScreenDto updateScreen(final ScreenDto screenDto, final String screenId) {
        Screen screenDB = findById(screenId);
        if(screenDB == null){
            throw new NotFoundException("Screen not found for this screenId : "+ screenId);
        }
        screenDB.setName(screenDto.getName());
        screenDB.setTheatreId(screenDto.getTheatreId());
        Screen screen = screenRepository.save(screenDB);

        List<SeatDto> existingSeatDtos = seatUseCase.getSeatDtoByScreenId(screenId);
        ScreenDto updatedScreenDto = modelMapper.map(screen, ScreenDto.class);
        List<SeatDto> seatDtos = screenDto.getSeats().stream().map(seatDto -> mapAndProcessSeatDto(existingSeatDtos, seatDto, screenId)).collect(Collectors.toList());
        updatedScreenDto.setSeats(seatDtos);
        return updatedScreenDto;
    }

    private SeatDto mapAndProcessSeatDto(final List<SeatDto> existingSeats, final SeatDto newSeat, final String screenId) {
        final Optional<SeatDto> foundSeatDto = existingSeats.stream().filter(existingSeat -> existingSeat.getId().equals(newSeat.getId())).findFirst();
        if(foundSeatDto.isPresent()){
            SeatDto existingSeatDto = foundSeatDto.get();
            existingSeatDto.setRowNo(newSeat.getRowNo());
            existingSeatDto.setSeatNo(newSeat.getSeatNo());
            existingSeatDto.setPrice(newSeat.getPrice());
            existingSeatDto.setScreenId(screenId);
            return seatUseCase.saveSeat(existingSeatDto);
        }
        newSeat.setScreenId(screenId);
        return seatUseCase.saveSeat(newSeat);
    }
}
