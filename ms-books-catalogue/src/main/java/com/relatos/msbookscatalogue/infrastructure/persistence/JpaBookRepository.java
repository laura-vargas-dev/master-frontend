package com.relatos.msbookscatalogue.infrastructure.persistence;

import com.relatos.msbookscatalogue.domain.model.Book;
import com.relatos.msbookscatalogue.domain.repository.BookRepository;
import com.relatos.msbookscatalogue.interfaces.mapper.BookMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional
public class JpaBookRepository implements BookRepository {

    private final SpringDataBookRepository springRepo;
    private final BookMapper mapper;  // Mapea entre BookEntity <-> Book

    public JpaBookRepository(SpringDataBookRepository springRepo, BookMapper mapper) {
        this.springRepo = springRepo;
        this.mapper = mapper;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return springRepo.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Book> findAll() {
        return springRepo.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAllVisible() {
        return springRepo.findByVisibleTrue().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return springRepo.findByIsbn(isbn)
                .map(mapper::toDomain);
    }

    @Override
    public Book save(Book book) {
        BookEntity entity = mapper.toEntity(book);
        BookEntity saved = springRepo.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        springRepo.deleteById(id);
    }
}