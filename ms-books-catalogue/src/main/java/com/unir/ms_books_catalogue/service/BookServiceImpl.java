package com.unir.ms_books_catalogue.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unir.ms_books_catalogue.controller.model.BookDto;
import com.unir.ms_books_catalogue.controller.model.CreateBookRequest;
import com.unir.ms_books_catalogue.data.BookRepository;
import com.unir.ms_books_catalogue.data.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookServiceImpl implements BooksService {

    @Autowired
    private BookRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Book> getBooks(String title, String author, String publicationDate, String category,
                               String isbn, Double rating, Boolean visible, Integer stock, Double price) {
        if (StringUtils.hasLength(title) || StringUtils.hasLength(author) || publicationDate != null
                || StringUtils.hasLength(category) || StringUtils.hasLength(isbn) || rating != null
                || visible != null || stock != null || price != null) {
            return repository.search(title, author, publicationDate != null ? LocalDate.parse(publicationDate) : null,
                    category, isbn, rating, visible, stock, price);
        }
        List<Book> books = repository.getBooks();
        return books.isEmpty() ? null : books;
    }

    @Override
    public Book getBook(String bookId) {
        return repository.getById(Long.valueOf(bookId));
    }

    @Override
    public Book createBook(CreateBookRequest request) {
        if (request != null && StringUtils.hasLength(request.getTitle().trim())
                && StringUtils.hasLength(request.getAuthor().trim())
                && request.getPublicationDate() != null
                && StringUtils.hasLength(request.getCategory().trim())
                && StringUtils.hasLength(request.getIsbn().trim())
                && request.getRating() != null
                && request.getVisible() != null
                && request.getStock() != null
                && request.getPrice() != null) {
            Book book = Book.builder().title(request.getTitle()).author(request.getAuthor())
                    .category(request.getCategory()).publicationDate(request.getPublicationDate())
                    .isbn(request.getIsbn()).rating(Double.valueOf(request.getRating()))
                    .visible(request.getVisible()).stock(request.getStock())
                    .price(request.getPrice()).build();
            return repository.save(book);
        } else {
            return null;
        }
    }

    @Override
    public Book updateBook(String productId, BookDto updateRequest) {
        Book book = repository.getById(Long.valueOf(productId));
        if (book != null) {
            book.update(updateRequest);
            repository.save(book);
            return book;
        } else {
            return null;
        }
    }

    @Override
    public Book updateBook(String bookId, String updateRequest) {
        Book book = repository.getById(Long.valueOf(bookId));
        if (book != null) {
            try {
                log.info(String.valueOf(objectMapper.writeValueAsString(book)));
                JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(updateRequest));
                JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(book)));
                Book patched = objectMapper.treeToValue(target, Book.class);
                repository.save(patched);
                return patched;
            } catch (JsonProcessingException | JsonPatchException e) {
                log.error("Error updating book {}", bookId, e);
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Boolean removeBook(String bookId) {
        Book book = repository.getById(Long.valueOf(bookId));

        if (book != null) {
            repository.delete(book);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
