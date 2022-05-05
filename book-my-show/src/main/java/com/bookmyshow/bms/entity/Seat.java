package com.bookmyshow.bms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "seat")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Seat {

    @Id
    private String id;

    @Field("rowNo")
    private Integer rowNo;

    @Field("seatNo")
    private Integer seatNo;

    @Field("price")
    private Double price;

    @Field("screenId")
    private String screenId;
}
