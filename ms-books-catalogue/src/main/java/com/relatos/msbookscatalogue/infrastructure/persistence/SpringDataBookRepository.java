package com.relatos.msbookscatalogue.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataBookRepository
        extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByVisibleTrue();           // Método derivado para solo visibles

    Optional<BookEntity> findByIsbn(String isbn);   // Método derivado para buscar por ISBN
}