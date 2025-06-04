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

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Caso de uso: crear un nuevo libro
    @Transactional
    public BookDto createBook(CreateBookCommand cmd) {
        // Pudimos validar reglas de negocio aquí (por ejemplo, ISBN único)
        if (bookRepository.findByIsbn(cmd.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("ISBN ya existe");
        }
        // Construir la entidad de dominio
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

        Book saved = bookRepository.save(book);  // Se registra en DB
        return BookMapper.toDto(saved);          // Convertir a DTO para responder
    }

    // Caso de uso: listar todos los libros visibles
    public List<BookDto> listVisibleBooks() {
        return bookRepository.findAllVisible().stream()
                .map(BookMapper::toDto)
                .collect(Collectors.toList());
    }

    // Caso de uso: obtener un libro por ID
    public BookDto getBookById(Long id) {
        return bookRepository.findById(id)
                .map(BookMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));
    }

    // Caso de uso: actualizar un libro completo
    @Transactional
    public BookDto updateBook(Long id, CreateBookCommand cmd) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));
        // Actualizar todos los campos usando setters de la entidad de dominio
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
        return BookMapper.toDto(updated);
    }

    // Caso de uso: eliminar un libro
    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Libro no encontrado");
        }
        bookRepository.deleteById(id);
    }
}