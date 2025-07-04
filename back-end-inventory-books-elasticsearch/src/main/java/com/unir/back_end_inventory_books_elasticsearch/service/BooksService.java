package com.unir.back_end_inventory_books_elasticsearch.service;

import com.unir.back_end_inventory_books_elasticsearch.controller.model.BooksQueryResponse;
import com.unir.back_end_inventory_books_elasticsearch.controller.model.CreateBookRequest;
import com.unir.back_end_inventory_books_elasticsearch.data.model.Book;

import java.util.List;

public interface BooksService {

    BooksQueryResponse getBooks(String title, String author, String category, Boolean visible);

    BooksQueryResponse findBooks (
            List<String> ratingValues,
            List<String> categoryValues,
            List<String> priceValues,
            List<String> stockValues,
            String title,
            String author,
            String page);

    Book getBook(String bookId);

    Boolean removeBook(String bookId);

    Book createBook(CreateBookRequest request);
}
