package com.relatos.msbookscatalogue.application.service;


import com.relatos.msbookscatalogue.application.command.CreateBookCommand;
import com.relatos.msbookscatalogue.application.dto.BookDto;
import com.relatos.msbookscatalogue.domain.model.Book;
import com.relatos.msbookscatalogue.domain.repository.BookRepository;
import com.relatos.msbookscatalogue.interfaces.mapper.BookMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Transactional
    public BookDto createBook(CreateBookCommand cmd) {
        if (bookRepository.findByIsbn(cmd.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("ISBN ya existe");
        }

        Book book = Book.builder()
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

        Book saved = bookRepository.save(book);
        return bookMapper.toDto(saved);
    }

    public List<BookDto> listVisibleBooks() {
        return bookRepository.findAllVisible().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDto getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));
    }

    @Transactional
    public BookDto updateBook(Long id, CreateBookCommand cmd) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));

        existing.setTitle(cmd.getTitle());
        existing.setAuthor(cmd.getAuthor());
        existing.setPublicationDate(cmd.getPublicationDate());
        existing.setCategory(cmd.getCategory());
        existing.setIsbn(cmd.getIsbn());
        existing.setRating(cmd.getRating());
        existing.setVisible(cmd.getVisible());
        existing.setStock(cmd.getStock());
        existing.setPrice(cmd.getPrice());

        Book updated = bookRepository.save(existing);
        return bookMapper.toDto(updated);
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Libro no encontrado");
        }
        bookRepository.deleteById(id);
    }
}