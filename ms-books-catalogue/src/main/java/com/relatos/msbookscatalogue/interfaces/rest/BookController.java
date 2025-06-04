package com.relatos.msbookscatalogue.interfaces.rest;



import com.relatos.msbookscatalogue.application.command.CreateBookCommand;
import com.relatos.msbookscatalogue.application.dto.BookDto;
import com.relatos.msbookscatalogue.application.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/books")
@Validated
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // POST /api/books
    @PostMapping
    public ResponseEntity<BookDto> createBook(
            @Valid @RequestBody CreateBookCommand cmd) {
        BookDto created = bookService.createBook(cmd);
        return ResponseEntity.status(201).body(created);
    }

    // GET /api/books
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllVisible() {
        List<BookDto> list = bookService.listVisibleBooks();
        return ResponseEntity.ok(list);
    }

    // GET /api/books/{id}
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getById(@PathVariable Long id) {
        try {
            BookDto dto = bookService.getBookById(id);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /api/books/{id}
    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody CreateBookCommand cmd) {
        try {
            BookDto updated = bookService.updateBook(id, cmd);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/books/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
