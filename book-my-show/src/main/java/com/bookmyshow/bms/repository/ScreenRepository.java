package com.bookmyshow.bms.repository;

import com.bookmyshow.bms.entity.Screen;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenRepository extends MongoRepository<Screen, String> {

    List<Screen> findByTheatreId(final String theatreId);
}
