package com.bookmyshow.bms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ScreenDto {

    private String id;

    private String name;

    private String theatreId;

    private List<SeatDto> seats = Collections.emptyList();

}
