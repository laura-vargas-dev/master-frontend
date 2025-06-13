package com.unir.ms_books_catalogue.data;

import com.unir.ms_books_catalogue.controller.model.BookDto;
import com.unir.ms_books_catalogue.data.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface BookJpaRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    List<Book> findByTitle(String title);

    List<Book> findByAuthor(String author);

    List<Book> findByPublicationDate(LocalDate publicationDate);

    List<Book> findByCategory(String category);

    List<Book> findByIsbn(String isbn);

    List<Book> findByRating(Double rating);

    List<Book> findByVisible(Boolean visible);

    List<Book> findByStock(Integer stock);

    List<Book> findByPrice(Double price);

    List<Book> findByTitleAndAuthor(String title, String author);
}
