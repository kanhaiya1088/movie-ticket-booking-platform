package com.bookmyshow.bms.service;

import com.bookmyshow.bms.dto.MovieDto;
import com.bookmyshow.bms.entity.Movie;
import com.bookmyshow.bms.exceptions.NotFoundException;
import com.bookmyshow.bms.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MovieUseCase {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ModelMapper modelMapper;


    public MovieDto getMovie(final String movieId) {
        Movie movie = findById(movieId);
        if(movie == null){
            throw new NotFoundException("Movie not found for this movieId : "+ movieId);
        }
        return modelMapper.map(movie, MovieDto.class);
    }

    public MovieDto saveMovie(final MovieDto movieDto) {
        Movie movie = modelMapper.map(movieDto, Movie.class);
        Movie dbMovie = movieRepository.save(movie);
        return modelMapper.map(dbMovie, MovieDto.class);
    }

    public List<MovieDto> getAllMovie() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieDto> movieDtos = movies.stream().map(movie -> modelMapper.map(movie, MovieDto.class)).collect(Collectors.toList());
        return movieDtos;
    }

    public Movie findById(final String id) {
        return movieRepository.findById(id).orElse(null);
    }

    public MovieDto updateMovie(final MovieDto movieDto, final String movieId) {
        Movie movie = findById(movieId);
        if(movie == null){
            throw new NotFoundException("Movie not found for this movieId : "+ movieId);
        }
        MovieDto updatedMovieDto = MovieDto.builder().id(movieId).name(movieDto.getName()).genre(movieDto.getGenre()).language(movieDto.getLanguage()).build();
        return saveMovie(updatedMovieDto);
    }
}
