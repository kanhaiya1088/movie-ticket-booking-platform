package com.bookmyshow.bms.service;

import com.bookmyshow.bms.dto.BookingDto;
import com.bookmyshow.bms.dto.SeatDto;
import com.bookmyshow.bms.dto.ShowDto;
import com.bookmyshow.bms.entity.Booking;
import com.bookmyshow.bms.exceptions.NotFoundException;
import com.bookmyshow.bms.exceptions.SeatUnavailableException;
import com.bookmyshow.bms.repository.BookingRepository;
import com.bookmyshow.bms.utils.BookingStatus;
import com.bookmyshow.bms.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookingUseCase {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ShowUseCase showUseCase;

    @Autowired
    private SeatUseCase seatUseCase;

    @Autowired
    private TheatreUseCase theatreUseCase;

    @Autowired
    private ScreenUseCase screenUseCase;

    @Autowired
    private MovieUseCase movieUseCase;

    /**
     * Single and bulk booking implementation.
     */
    public BookingDto createBooking(final BookingDto bookingDto, final String userName) {
        final ShowDto rquestedShow = bookingDto.getShow();
        final List<String> requestedSeatIds = bookingDto.getSeats().stream().map(SeatDto::getId).collect(Collectors.toList());
        final ShowDto fetchedShowDto = showUseCase.getShowDto(rquestedShow.getId());
        final List<Booking> existingBookings = bookingRepository.findByShowIdAndBookingStatus(rquestedShow.getId(), BookingStatus.Confirmed);
        if (isAnySeatAlreadyBooked(requestedSeatIds, existingBookings)) {
            throw new SeatUnavailableException("Seat Already Booked");
        }
        final Booking bookingRequest = new Booking();
        bookingRequest.setShowId(rquestedShow.getId());
        bookingRequest.setNoOfSeats(bookingDto.getSeats().size());
        bookingRequest.setSeatIds(requestedSeatIds);
        bookingRequest.setUserName(bookingDto.getUserName());
        bookingRequest.setBookingStatus(BookingStatus.Created);
        final Double totalCost = getTotalCost(fetchedShowDto,requestedSeatIds, rquestedShow);
        bookingRequest.setTotalCost(totalCost);
        bookingRequest.setBookingDate(new Date());
        bookingRequest.setBookingModifiedDate(new Date());
        final Booking booking = bookingRepository.save(bookingRequest);

        // TODO after booking creation successfully we'll send request to payment service with totalAmount and bookingId where payment gateway interaction happens
        // Then after successfully payment completed after again request will come with booking id into our this service for booking confirmation.

        bookingDto.setId(booking.getId());

        return bookingDto;
    }

    private boolean isAnySeatAlreadyBooked(final List<String> requestedSeatIds, final List<Booking> existingBookings) {
        final List<String> bookedSeatIds = existingBookings.stream().map(Booking::getSeatIds).flatMap(Collection::stream).collect(Collectors.toList());
        final boolean isSeatBooked = requestedSeatIds.stream().anyMatch(seatId-> bookedSeatIds.contains(seatId));
        return isSeatBooked;
    }

    private Double getTotalCost(final ShowDto fetchedShowDto, final List<String> requestedSeatIds, final ShowDto requestedShowDto){
        final Map<String, Double> currentSeatPriceMap = new LinkedHashMap<>();
        for(String seatId : requestedSeatIds){
            SeatDto seat = seatUseCase.getSeatDto(seatId);
            currentSeatPriceMap.put(seatId, seat.getPrice());
        }
        double amount = 0;
        if(DateUtils.isAfternoonShow(requestedShowDto.getStartTime())){
            amount = totalCostAfter20PercentDiscount(currentSeatPriceMap);
        } else {
            amount = totalCostAfter50PercentDiscountOnThirdTicketIfApplicable(currentSeatPriceMap);
        }
        return amount;
    }

    /**
     * Calculate totalCost after 50% discount on third Ticket if applicable
     */
    private Double totalCostAfter50PercentDiscountOnThirdTicketIfApplicable(final Map<String, Double> cuurentSeatPriceMap){
        double amount = 0;
        int i = 1;
        for (Map.Entry<String, Double> entry : cuurentSeatPriceMap.entrySet()) {
            if(i == 3) {
                amount = amount + (entry.getValue() - (entry.getValue()*50)/100);
            } else{
                amount = amount + entry.getValue();
            }
            i++;
        }
        return amount;
    }

    /**
     *  Calculate 20% discount of total amount for Afternoon shows
     */
    private Double totalCostAfter20PercentDiscount(final Map<String, Double> currentSeatPriceMap){
        double totalAmount = currentSeatPriceMap.values().stream().mapToDouble(Double::doubleValue).sum();
        double finalAmount = totalAmount - (totalAmount*20)/100 ;
        return finalAmount;
    }

    public BookingDto getBooking(final String bookingId) {
        final Booking booking = findById(bookingId);
        if(booking == null){
            throw new NotFoundException("Booking not found for this bookingId : "+ bookingId);
        }
        final BookingDto bookingDto = modelMapper.map(booking, BookingDto.class);
        final ShowDto showDto = showUseCase.getShowDto(bookingDto.getShow().getId());
        final List<SeatDto> seatDtos = booking.getSeatIds().stream().map(seatId -> seatUseCase.getSeatDto(seatId)).collect(Collectors.toList());
        bookingDto.setShow(showDto);
        bookingDto.setSeats(seatDtos);
        return bookingDto;
    }

    public Booking findById(final String id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public BookingDto cancelBooking(final String bookingId) {
        final Booking booking = findById(bookingId);
        if(booking == null){
            throw new NotFoundException("Booking not found for this bookingId : "+ bookingId);
        }
        booking.setBookingStatus(BookingStatus.Cancelled);
        bookingRepository.save(booking);
        // TODO Then request will go on payment-service for refund initiation
        final BookingDto bookingDto = modelMapper.map(booking, BookingDto.class);
        final ShowDto showDto = showUseCase.getShowDto(bookingDto.getShow().getId());
        final List<SeatDto> seatDtos = booking.getSeatIds().stream().map(seatId -> seatUseCase.getSeatDto(seatId)).collect(Collectors.toList());
        bookingDto.setShow(showDto);
        bookingDto.setSeats(seatDtos);
        return bookingDto;
    }
}
