package com.unir.ms_books_catalogue.controller;

import com.unir.ms_books_catalogue.controller.model.BookDto;
import com.unir.ms_books_catalogue.controller.model.CreateBookRequest;
import com.unir.ms_books_catalogue.data.model.Book;
import com.unir.ms_books_catalogue.service.BooksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTests {

    @Mock
    private BooksService booksService;

    @InjectMocks
    private BookController bookController;

    private CreateBookRequest testRequest;
    private Book testBook;

    @BeforeEach
    void setUp() {
        testRequest = new CreateBookRequest();
        testRequest.setTitle("Test Book");
        testRequest.setAuthor("Test Author");
        testRequest.setPublicationDate(LocalDate.now());
        testRequest.setCategory("Fiction");
        testRequest.setIsbn("1234567890");
        testRequest.setRating(4.5);
        testRequest.setVisible(true);
        testRequest.setStock(10);
        testRequest.setPrice(19.99);

        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setPublicationDate(LocalDate.now());
        testBook.setCategory("Fiction");
        testBook.setIsbn("1234567890");
        testBook.setRating(4.5);
        testBook.setVisible(true);
        testBook.setStock(10);
        testBook.setPrice(19.99);
    }

    @Test
    void testCreateBook() {
        when(booksService.createBook(testRequest)).thenReturn(testBook);

        ResponseEntity<Book> response = bookController.addBook(testRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testBook, response.getBody());
        verify(booksService, times(1)).createBook(testRequest);
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = new ArrayList<>();
        books.add(testBook);

        when(booksService.getBooks(null, null, null, null,
                null, null, null, null, null)).thenReturn(books);

        ResponseEntity<List<Book>> response = bookController.getBooks(null, null, null,
                null,null, null, null, null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
        verify(booksService, times(1)).getBooks(null, null, null,
                null,null, null, null, null, null);
    }

    @Test
    void testGetBookById() {
        when(booksService.getBook("1")).thenReturn(testBook);

        ResponseEntity<Book> response = bookController.getBook("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testBook, response.getBody());
        verify(booksService, times(1)).getBook("1");
    }
}
