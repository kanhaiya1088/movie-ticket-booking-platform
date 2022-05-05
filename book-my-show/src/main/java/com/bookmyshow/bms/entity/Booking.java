package com.bookmyshow.bms.entity;

import com.bookmyshow.bms.utils.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Document(collection = "booking")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Booking {

    @Id
    private String id;

    @Field("showId")
    private String showId;

    @Field("noOfSeats")
    private Integer noOfSeats;

    @Field("seatIds")
    private List<String> seatIds;

    @Field("userName")
    private String userName;

    @Field("bookingStatus")
    private BookingStatus bookingStatus;

    @Field("totalCost")
    private Double totalCost;

    @Field("bookingDate")
    private Date bookingDate;

    @Field("bookingModifiedDate")
    @LastModifiedDate
    private Date bookingModifiedDate;


}