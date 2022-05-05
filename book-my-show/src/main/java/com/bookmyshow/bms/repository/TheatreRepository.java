package com.bookmyshow.bms.repository;

import com.bookmyshow.bms.entity.Theatre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheatreRepository extends MongoRepository<Theatre, String> {

    List<Theatre> findByCity(final String city);

}
