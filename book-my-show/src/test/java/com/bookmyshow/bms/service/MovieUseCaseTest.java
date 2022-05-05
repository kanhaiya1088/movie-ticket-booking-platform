package com.bookmyshow.bms.service;

import com.bookmyshow.bms.Utils.TestDataPreparation;
import com.bookmyshow.bms.dto.MovieDto;
import com.bookmyshow.bms.entity.Movie;
import com.bookmyshow.bms.repository.MovieRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MovieUseCaseTest {

    @Mock
    private MovieRepository movieRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private MovieUseCase movieUseCase;

    @Test
    void getMovieTest(){
        Movie movie = TestDataPreparation.getMovie();

        Mockito.when(movieRepository.findById("1")).thenReturn(Optional.of(movie));

        MovieDto result = movieUseCase.getMovie("1");
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo("1");
        Assertions.assertThat(result.getName()).isEqualTo("Movie-1");
        Assertions.assertThat(result.getGenre()).isEqualTo("Acation");
        Assertions.assertThat(result.getLanguage()).isEqualTo("Hindi");
    }

    @Test
    void saveMovieTest(){
        Movie movie = TestDataPreparation.getMovie();
        Mockito.when(movieRepository.save(Mockito.any())).thenReturn(movie);

        MovieDto movieDto = new MovieDto(null, "Movie-1", "Acation", "Hindi");
        MovieDto result = movieUseCase.saveMovie(movieDto);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo("1");
        Assertions.assertThat(result.getName()).isEqualTo("Movie-1");
        Assertions.assertThat(result.getGenre()).isEqualTo("Acation");
        Assertions.assertThat(result.getLanguage()).isEqualTo("Hindi");
    }
}
