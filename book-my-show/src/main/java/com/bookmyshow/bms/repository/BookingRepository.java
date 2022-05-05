package com.bookmyshow.bms.repository;

import com.bookmyshow.bms.entity.Booking;
import com.bookmyshow.bms.utils.BookingStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {

    /**
     * Return all bookings of same show which are confirmed booking,
     * for calculation of available seats for the same show
     * @param showId
     * @param bookingStatus
     */
    List<Booking> findByShowIdAndBookingStatus(final String showId, final BookingStatus bookingStatus);

}
