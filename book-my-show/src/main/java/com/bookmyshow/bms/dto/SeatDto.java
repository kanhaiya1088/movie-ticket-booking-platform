package com.bookmyshow.bms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SeatDto {

    private String id;
    private Integer rowNo;
    private Integer seatNo;
    private Double price;
    private String screenId;
}
