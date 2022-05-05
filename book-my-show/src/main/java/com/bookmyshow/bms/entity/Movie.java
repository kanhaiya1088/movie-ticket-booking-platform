package com.bookmyshow.bms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "movie")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movie {

	@Id
	private String id;

	@Field("name")
	private String name;

	@Field("genre")
	private String genre;

	@Field("language")
	private String language;

}
