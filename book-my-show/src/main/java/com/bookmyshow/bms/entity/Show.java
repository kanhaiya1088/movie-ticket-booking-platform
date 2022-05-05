package com.bookmyshow.bms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "show")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Show {

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("movieId")
    private String movieId;

    @Field("screenId")
    private String screenId;

    @Field("theatreId")
    private String theatreId;

    @Field("startTime")
    private Date startTime;

    @Field("endTime")
    private Date endTime;

    @Field("durationInMinutes")
    private Integer durationInMinutes;
}
