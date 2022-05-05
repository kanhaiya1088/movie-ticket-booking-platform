package com.bookmyshow.bms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "screen")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Screen {

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("theatreId")
    private String theatreId;

}
