package com.unir.ms_books_catalogue.service;

import com.unir.ms_books_catalogue.controller.model.BookDto;
import com.unir.ms_books_catalogue.controller.model.CreateBookRequest;
import com.unir.ms_books_catalogue.data.model.Book;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BooksService {

    List<Book> getBooks(String title, String author, String publicationDate, String category,
                        String isbn, Double rating, Boolean visible, Integer stock, Double price);

    Book getBook(String bookId);

    Book createBook(CreateBookRequest request);

    Book updateBook(String bookId, BookDto updateRequest);

    Book updateBook(String bookId, String updateRequest);

    Boolean removeBook(String bookId);
}
