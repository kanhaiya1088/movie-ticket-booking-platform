package com.bookmyshow.bms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShowDto {

    private String id;
    private String name;
    private MovieDto movie;
    private ScreenDto screen;
    private String theatreId;
    private Date startTime;
    private Date endTime;
    private Integer durationInMinutes;
}
