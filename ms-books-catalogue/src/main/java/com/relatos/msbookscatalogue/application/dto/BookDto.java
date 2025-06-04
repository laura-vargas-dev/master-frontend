package com.relatos.msbookscatalogue.application.dto;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookDto {

    private Long id;
    private String title;
    private String author;
    private LocalDate publicationDate;
    private String category;
    private String isbn;
    private Integer rating;
    private Boolean visible;
    private Integer stock;
    private Double price;
}