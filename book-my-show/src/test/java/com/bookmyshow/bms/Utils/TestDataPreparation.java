package com.bookmyshow.bms.Utils;

import com.bookmyshow.bms.dto.ScreenDto;
import com.bookmyshow.bms.dto.SeatDto;
import com.bookmyshow.bms.dto.TheatreDto;
import com.bookmyshow.bms.entity.Movie;
import com.bookmyshow.bms.entity.Screen;
import com.bookmyshow.bms.entity.Seat;
import com.bookmyshow.bms.entity.Theatre;

import java.util.Arrays;
import java.util.List;

public class TestDataPreparation {

    public static Movie getMovie(){
        return new Movie("1", "Movie-1", "Acation", "Hindi");
    }

    public static List<Movie> getMovieList(){
        return Arrays.asList(getMovie());
    }

    public static List<Seat> getSeats(){
        Seat seat1 = new Seat("1", 1, 1, 100.0, "Screen-1");
        Seat seat2 = new Seat("2", 1, 2, 100.0, "Screen-1");
        return Arrays.asList(seat1, seat2);
    }

    public static List<SeatDto> getSeatDtos(){
        SeatDto seatDto1 = new SeatDto("1", 1, 1, 100.0, "Screen-1");
        SeatDto seatDto2 = new SeatDto("2", 1, 2, 100.0, "Screen-1");
        return Arrays.asList(seatDto1, seatDto2);
    }

    public static Screen getScreen(){
        return new Screen("Screen-1", "Screen-Name-1", "Theatre-1");
    }

    public static List<Screen> getScreenList(){
        return Arrays.asList(getScreen());
    }

    public static ScreenDto getScreenDto(){
        ScreenDto screenDtos = new ScreenDto("Screen-1", "Screen-Name-1", "Theatre-1", getSeatDtos());
        return screenDtos;
    }

    public static List<ScreenDto> getScreenDtoList(){
        return Arrays.asList(getScreenDto());
    }

    public static Theatre getTheatre(){
        return new Theatre("1", "Theatre-Name-1", "Theatre-City-1");
    }

    public static TheatreDto getTheatreDto(){
        return new TheatreDto("1", "Theatre-Name-1", "Theatre-City-1", getScreenDtoList());
    }
}
