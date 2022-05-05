package com.bookmyshow.bms.service;

import com.bookmyshow.bms.Utils.TestDataPreparation;
import com.bookmyshow.bms.dto.ScreenDto;
import com.bookmyshow.bms.entity.Screen;
import com.bookmyshow.bms.repository.ScreenRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ScreenUseCaseTest {

    @Mock
    private ScreenRepository screenRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private ScreenUseCase screenUseCase;

    @Mock
    private SeatUseCase seatUseCase;

    @Test
    void getAllScreenByTheatreIdTest(){

        List<Screen> screenList = TestDataPreparation.getScreenList();
        Mockito.when(screenRepository.findByTheatreId(Mockito.any())).thenReturn(screenList);
        Mockito.when(seatUseCase.getSeatDtoByScreenId(Mockito.anyString())).thenReturn(TestDataPreparation.getSeatDtos());

        List<ScreenDto> result = screenUseCase.getAllScreenByTheatreId("Theatre-1");
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.get(0).getId()).isEqualTo("Screen-1");
        Assertions.assertThat(result.get(0).getName()).isEqualTo("Screen-Name-1");
        Assertions.assertThat(result.get(0).getTheatreId()).isEqualTo("Theatre-1");
        Assertions.assertThat(result.get(0).getSeats()).isNotEmpty();
        Assertions.assertThat(result.get(0).getSeats().get(0).getId()).isEqualTo("1");
        Assertions.assertThat(result.get(0).getSeats().get(0).getRowNo()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getSeats().get(0).getSeatNo()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getSeats().get(0).getPrice()).isEqualTo(100D);
        Assertions.assertThat(result.get(0).getSeats().get(0).getScreenId()).isEqualTo("Screen-1");
    }


}
