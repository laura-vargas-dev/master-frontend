package com.relatos.msbookscatalogue.domain.repository;


import com.relatos.msbookscatalogue.domain.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> findById(Long id);                  // Buscar por ID
    List<Book> findAll();                              // Listar todos
    List<Book> findAllVisible();                       // Listar solo visibles
    Optional<Book> findByIsbn(String isbn);            // Buscar por ISBN único
    Book save(Book book);                               // Crear o actualizar
    void deleteById(Long id);                          // Eliminar por ID
}
