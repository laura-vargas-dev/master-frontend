package com.unir.ms_books_catalogue.controller;

import com.unir.ms_books_catalogue.controller.model.BookDto;
import com.unir.ms_books_catalogue.controller.model.CreateBookRequest;
import com.unir.ms_books_catalogue.data.model.Book;
import com.unir.ms_books_catalogue.service.BooksService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/books")
public class BookController {
    private final BooksService service;

    // GET /api/books
    @GetMapping
    public ResponseEntity<List<Book>> getBooks(
            @RequestHeader Map<String, String> headers,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String publicationDate,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) Boolean visible,
            @RequestParam(required = false) Integer stock,
            @RequestParam(required = false) Double price) {
        log.info("headers: {}", headers);
        List<Book> books = service.getBooks(title, author, publicationDate, category, isbn,
                rating, visible, stock, price);
        return ResponseEntity.ok(Objects.requireNonNullElse(books, Collections.emptyList()));
    }

    // GET /api/books/{bookId}
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable String bookId) {
        log.info("Request received for book {}", bookId);
        Book book = service.getBook(bookId);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/books
    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody CreateBookRequest request) {
        Book createdBook = service.createBook(request);
        if (createdBook != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT /api/books/{bookId}
    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable String bookId, @RequestBody BookDto body) {
        Book updated = service.updateBook(bookId, body);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PATCH /api/books/{bookId}
    @PatchMapping("/{bookId}")
    public ResponseEntity<Book> patchBook(@PathVariable String bookId, @RequestBody String patchBody) {
        Book patched = service.updateBook(bookId, patchBody);
        if (patched != null) {
            return ResponseEntity.ok(patched);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    // DELETE /api/books/{bookId}
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable String bookId) {
        Boolean removed = service.removeBook(bookId);
        if (Boolean.TRUE.equals(removed)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
