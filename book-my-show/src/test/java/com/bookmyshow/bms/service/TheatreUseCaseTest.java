package com.bookmyshow.bms.service;

import com.bookmyshow.bms.Utils.TestDataPreparation;
import com.bookmyshow.bms.dto.TheatreDto;
import com.bookmyshow.bms.repository.TheatreRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class TheatreUseCaseTest {

    @Mock
    private TheatreRepository theatreRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private TheatreUseCase theatreUseCase;

    @Mock
    private ScreenUseCase screenUseCase;

    @Test
    void createTheatreWithAllDataTest(){
        TheatreDto theatreDto = TestDataPreparation.getTheatreDto();

        Mockito.when(theatreRepository.save(Mockito.any())).thenReturn(TestDataPreparation.getTheatre());
        Mockito.when(screenUseCase.saveScreen(Mockito.any())).thenReturn(TestDataPreparation.getScreenDto());
        TheatreDto result = theatreUseCase.createTheatre(theatreDto);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo("1");
        Assertions.assertThat(result.getName()).isEqualTo("Theatre-Name-1");
        Assertions.assertThat(result.getCity()).isEqualTo("Theatre-City-1");
        Assertions.assertThat(result.getScreens()).isNotEmpty();
        Assertions.assertThat(result.getScreens().get(0).getName()).isEqualTo("Screen-Name-1");
        Assertions.assertThat(result.getScreens().get(0).getTheatreId()).isEqualTo("Theatre-1");
        Assertions.assertThat(result.getScreens().get(0).getSeats()).isNotEmpty();
        Assertions.assertThat(result.getScreens().get(0).getSeats().get(0).getRowNo()).isEqualTo(1);
        Assertions.assertThat(result.getScreens().get(0).getSeats().get(0).getSeatNo()).isEqualTo(1);
        Assertions.assertThat(result.getScreens().get(0).getSeats().get(0).getPrice()).isEqualTo(100D);
    }


}
