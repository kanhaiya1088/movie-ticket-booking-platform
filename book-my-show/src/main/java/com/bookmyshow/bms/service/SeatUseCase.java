package com.bookmyshow.bms.service;

import com.bookmyshow.bms.dto.SeatDto;
import com.bookmyshow.bms.entity.Seat;
import com.bookmyshow.bms.exceptions.NotFoundException;
import com.bookmyshow.bms.repository.SeatRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SeatUseCase {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SeatRepository seatRepository;


    public SeatDto getSeatDto(final String seatId) {
        Seat seat = findById(seatId);
        if(seat == null){
            throw new NotFoundException("Seat not found for this seatId : "+ seatId);
        }
        return modelMapper.map(seat, SeatDto.class);
    }

    public List<SeatDto> getSeatDtoByScreenId(final String screenId) {
        List<Seat> seats = seatRepository.findByScreenId(screenId);
        List<SeatDto> seatDtos = seats.stream().map(seat -> modelMapper.map(seat, SeatDto.class)).collect(Collectors.toList());
        return seatDtos;
    }

    @Transactional
    public SeatDto saveSeat(final SeatDto seatDto) {
        Seat seatData = modelMapper.map(seatDto, Seat.class);
        Seat seat = seatRepository.save(seatData);
        return modelMapper.map(seat, SeatDto.class);
    }

    @Transactional
    public SeatDto updateSeat(final SeatDto seatDto, final String seatId) {
        Seat seat = findById(seatId);
        if(seat == null){
            throw new NotFoundException("Seat not found for this seatId : "+ seatId);
        }
        SeatDto updatedSeatDto = SeatDto.builder().id(seatId).rowNo(seatDto.getRowNo()).seatNo(seatDto.getSeatNo()).price(seatDto.getPrice()).screenId(seatDto.getScreenId()).build();
        return saveSeat(updatedSeatDto);
    }

    public Seat findById(final String id) {
        return seatRepository.findById(id).orElse(null);
    }

    /**
     * Save Seats for given screenId
     */
    @Transactional
    public List<SeatDto> saveSeatsForScreen(final List<SeatDto> seatDtos, final String screenId) {
        final List<Seat> seats = seatDtos.stream().map(seatDto -> modelMapper.map(seatDto, Seat.class)).collect(Collectors.toList());
        seats.stream().forEach(seat -> seat.setScreenId(screenId));
        final List<Seat> dbSeats = seatRepository.saveAll(seats);
        List<SeatDto> seatDtoList = dbSeats.stream().map(dbSeat -> modelMapper.map(dbSeat, SeatDto.class)).collect(Collectors.toList());
        return seatDtoList;
    }
}
