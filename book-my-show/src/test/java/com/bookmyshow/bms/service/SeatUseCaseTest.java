package com.bookmyshow.bms.service;

import com.bookmyshow.bms.Utils.TestDataPreparation;
import com.bookmyshow.bms.dto.SeatDto;
import com.bookmyshow.bms.entity.Seat;
import com.bookmyshow.bms.repository.SeatRepository;
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
public class SeatUseCaseTest {

    @Mock
    private SeatRepository seatRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private SeatUseCase seatUseCase;

    @Test
    void getSeatsByScreenIdTest(){
        List<Seat> seats = TestDataPreparation.getSeats();

        Mockito.when(seatRepository.findByScreenId("Screen-1")).thenReturn(seats);

        List<SeatDto> result = seatUseCase.getSeatDtoByScreenId("Screen-1");
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.get(0).getId()).isEqualTo("1");
        Assertions.assertThat(result.get(0).getRowNo()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getSeatNo()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getPrice()).isEqualTo(100D);
        Assertions.assertThat(result.get(0).getScreenId()).isEqualTo("Screen-1");
    }

    @Test
    void saveSeatsByScreenIdTest(){
        List<SeatDto> seatDtos = TestDataPreparation.getSeatDtos();
        List<Seat> seats = TestDataPreparation.getSeats();

        Mockito.when(seatRepository.saveAll(Mockito.any())).thenReturn(seats);

        List<SeatDto> result = seatUseCase.saveSeatsForScreen(seatDtos,"Screen-1");
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.get(0).getId()).isEqualTo("1");
        Assertions.assertThat(result.get(0).getRowNo()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getSeatNo()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getPrice()).isEqualTo(100D);
        Assertions.assertThat(result.get(0).getScreenId()).isEqualTo("Screen-1");
    }


}
