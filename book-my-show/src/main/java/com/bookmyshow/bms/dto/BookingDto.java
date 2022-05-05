package com.bookmyshow.bms.dto;

import com.bookmyshow.bms.utils.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookingDto {

    private String id;
    private ShowDto show;
    private Integer noOfSeats;
    private List<SeatDto> seats;
    private String userName;
    private BookingStatus bookingStatus = BookingStatus.Created;
    private Double totalCost;
    private Date bookingDate;
    private Date bookingModifiedDate;

}
