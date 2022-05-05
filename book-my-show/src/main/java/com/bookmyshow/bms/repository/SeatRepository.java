package com.bookmyshow.bms.repository;

import com.bookmyshow.bms.entity.Seat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends MongoRepository<Seat, String> {

    List<Seat> findByScreenId(final String screenId);

}
