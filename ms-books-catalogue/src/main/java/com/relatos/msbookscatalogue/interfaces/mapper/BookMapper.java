package com.relatos.msbookscatalogue.interfaces.mapper;

import com.relatos.msbookscatalogue.application.dto.BookDto;
import com.relatos.msbookscatalogue.domain.model.Book;
import com.relatos.msbookscatalogue.infrastructure.persistence.BookEntity;

import java.util.Objects;

public class BookMapper {

    // Convierte BookEntity -> Book (dominio)
    public Book toDomain(BookEntity entity) {
        if (Objects.isNull(entity)) return null;
        return Book.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .publicationDate(entity.getPublicationDate())
                .category(entity.getCategory())
                .isbn(entity.getIsbn())
                .rating(entity.getRating())
                .visible(entity.getVisible())
                .stock(entity.getStock())
                .price(entity.getPrice())
                .build();
    }

    // Convierte Book (dominio) -> BookEntity
    public static BookEntity toEntity(Book domain) {
        if (Objects.isNull(domain)) return null;
        return BookEntity.builder()
                .id(domain.getId())
                .title(domain.getTitle())
                .author(domain.getAuthor())
                .publicationDate(domain.getPublicationDate())
                .category(domain.getCategory())
                .isbn(domain.getIsbn())
                .rating(domain.getRating())
                .visible(domain.getVisible())
                .stock(domain.getStock())
                .price(domain.getPrice())
                .build();
    }

    // Convierte Book (dominio) -> BookDto (respuesta REST)
    public static BookDto toDto(Book domain) {
        if (Objects.isNull(domain)) return null;
        return BookDto.builder()
                .id(domain.getId())
                .title(domain.getTitle())
                .author(domain.getAuthor())
                .publicationDate(domain.getPublicationDate())
                .category(domain.getCategory())
                .isbn(domain.getIsbn())
                .rating(domain.getRating())
                .visible(domain.getVisible())
                .stock(domain.getStock())
                .price(domain.getPrice())
                .build();
    }
}