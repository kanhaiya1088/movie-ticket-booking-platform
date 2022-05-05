package com.bookmyshow.bms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TheatreShowDto {

    private String id;

    private String name;

    private String city;

    private List<ShowDto> shows = Collections.emptyList();

}
