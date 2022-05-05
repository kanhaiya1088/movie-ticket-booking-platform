package com.bookmyshow.bms.repository;

import com.bookmyshow.bms.entity.Show;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ShowRepository extends MongoRepository<Show, String> {

    List<Show> findByMovieId(final String movieId);

    @Query("{'movieId' : ?0, 'startTime' : { $gte: ?1 } }")
    List<Show> findByMovieIdAndDate(final String movieId, final Date date);

    List<Show> findByTheatreId(final String theatreId);

    List<Show> findByMovieIdAndTheatreId(final String movieId, final String theatreId);
}
