package com.unir.back_end_inventory_books_elasticsearch.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {

    private String id;

    private String title;

    private String author;

    private LocalDate publicationDate;

    private String category;

    private String isbn;

    private Double rating;

    private Boolean visible = true;

    private Integer stock = 0;

    private Double price = 0.0;

    private String imgUrl;

}
