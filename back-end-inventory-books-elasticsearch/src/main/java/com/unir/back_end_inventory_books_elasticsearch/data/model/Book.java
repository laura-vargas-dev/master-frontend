package com.unir.back_end_inventory_books_elasticsearch.data.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Document(indexName = "books", createIndex = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Book {

    @Id
    private String id;

    @Field(type = FieldType.Long, name = "id")
    private Long uid;

    @Field(type = FieldType.Text, name = "title")
    private String title;

    @Field(type = FieldType.Search_As_You_Type, name = "author")
    private String author;

    @Field(
            type = FieldType.Date,
            name = "publicationDate",
            format = DateFormat.custom,
            pattern = "yyyy-MM-dd"
    )
    private LocalDate publicationDate;

    @Field(type = FieldType.Keyword, name = "category")
    private String category;

    @Field(type = FieldType.Text, name = "isbn")
    private String isbn;

    @Field(type = FieldType.Double, name = "rating")
    private Double rating;

    @Field(type = FieldType.Boolean, name = "visible")
    private Boolean visible = true;

    @Field(type = FieldType.Integer, name = "stock")
    private Integer stock = 0;

    @Field(type = FieldType.Double, name = "price")
    private Double price = 0.0;

    @Field(type = FieldType.Text, name = "imgUrl")
    private String imgUrl;
}
