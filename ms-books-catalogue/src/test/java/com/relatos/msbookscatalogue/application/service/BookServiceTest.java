package com.relatos.msbookscatalogue.application.service;

import com.relatos.msbookscatalogue.application.command.CreateBookCommand;
import com.relatos.msbookscatalogue.domain.model.Book;
import com.relatos.msbookscatalogue.domain.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBook_Success() {
        CreateBookCommand cmd = CreateBookCommand.builder()
                .title("Test")
                .author("Autor")
                .publicationDate(LocalDate.now())
                .category("Ficción")
                .isbn("1234567890")
                .rating(5)
                .visible(true)
                .stock(10)
                .price(19.99)
                .build();

        Book bookDomain = Book.builder()
                .id(1L)
                .title(cmd.getTitle())
                .author(cmd.getAuthor())
                .publicationDate(cmd.getPublicationDate())
                .category(cmd.getCategory())
                .isbn(cmd.getIsbn())
                .rating(cmd.getRating())
                .visible(cmd.getVisible())
                .stock(cmd.getStock())
                .price(cmd.getPrice())
                .build();

        when(bookRepository.findByIsbn(cmd.getIsbn())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(bookDomain);

        var result = bookService.createBook(cmd);

        assertNotNull(result);
        assertEquals("Test", result.getTitle());
        verify(bookRepository).findByIsbn(cmd.getIsbn());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void createBook_DuplicateIsbn_Throws() {
        CreateBookCommand cmd = CreateBookCommand.builder()
                .title("Test")
                .author("Autor")
                .publicationDate(LocalDate.now())
                .category("Ficción")
                .isbn("1234567890")
                .rating(5)
                .visible(true)
                .stock(10)
                .price(19.99)
                .build();

        when(bookRepository.findByIsbn(cmd.getIsbn()))
                .thenReturn(Optional.of(Book.builder().id(1L).build()));

        assertThrows(IllegalArgumentException.class,
                () -> bookService.createBook(cmd));

        verify(bookRepository).findByIsbn(cmd.getIsbn());
        verify(bookRepository, never()).save(any(Book.class));
    }
}
