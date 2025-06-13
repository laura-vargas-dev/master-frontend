package com.unir.ms_books_payments.facade.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Book {

    private Long id;

    private String title;

    private String author;

    private LocalDate publicationDate;

    private String category;

    private String isbn;

    private Double rating;

    private Boolean visible;

    private Integer stock;

    private Double price;
}
