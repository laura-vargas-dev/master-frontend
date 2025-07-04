package com.unir.back_end_inventory_books_elasticsearch.controller;

import com.unir.back_end_inventory_books_elasticsearch.controller.model.BooksQueryResponse;
import com.unir.back_end_inventory_books_elasticsearch.controller.model.CreateBookRequest;
import com.unir.back_end_inventory_books_elasticsearch.data.model.Book;
import com.unir.back_end_inventory_books_elasticsearch.service.BooksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/elastic/books")
public class BooksController {

    private final BooksService service;

    // GET /elastic/books
    @GetMapping
    public ResponseEntity<BooksQueryResponse> getBooks(
            @RequestHeader Map<String, String> headers,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String category,
            @RequestParam(required = false, defaultValue = "false") Boolean visible) {

        log.info("headers: {}", headers);
        BooksQueryResponse products = service.getBooks(title, author, category, visible);
        return ResponseEntity.ok(products);
    }

    // GET /elastic/books/{bookId}
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable String bookId) {

        log.info("Request received for product {}", bookId);
        Book product = service.getBook(bookId);

        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /elastic/books
    @PostMapping
    public ResponseEntity<Book> getBook(@RequestBody CreateBookRequest request) {

        Book createdProduct = service.createBook(request);

        if (createdProduct != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // DELETE /elastic/books/{bookId}
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
