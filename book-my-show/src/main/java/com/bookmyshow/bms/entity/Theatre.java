package com.bookmyshow.bms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "theatre")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Theatre {

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("city")
    private String city;

    //Other theatre metadata.
}
