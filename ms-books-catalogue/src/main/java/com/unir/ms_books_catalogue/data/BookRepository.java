package com.unir.ms_books_catalogue.data;

import com.unir.ms_books_catalogue.data.model.Book;
import com.unir.ms_books_catalogue.data.utils.Consts;
import com.unir.ms_books_catalogue.data.utils.SearchCriteria;
import com.unir.ms_books_catalogue.data.utils.SearchOperation;
import com.unir.ms_books_catalogue.data.utils.SearchStatement;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {
    private final BookJpaRepository repository;

    public List<Book> getBooks() { return repository.findAll(); }

    public Book getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Book save(Book book) {
        return repository.save(book);
    }

    public void delete(Book book) {
        repository.delete(book);
    }

    public List<Book> search(String title, String author, LocalDate publicationDate, String category,
                             String isbn, Double rating, Boolean visible, Integer stock, Double price) {
        SearchCriteria<Book> spec = new SearchCriteria<>();

        if (StringUtils.isNotBlank(title)) {
            spec.add(new SearchStatement(Consts.TITLE, title, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(author)) {
            spec.add(new SearchStatement(Consts.AUTHOR, author, SearchOperation.MATCH));
        }

        if (publicationDate != null) {
            spec.add(new SearchStatement(Consts.PUBLICATION_DATE, publicationDate, SearchOperation.EQUAL));
        }

        if (StringUtils.isNotBlank(category)) {
            spec.add(new SearchStatement(Consts.CATEGORY, category, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(isbn)) {
            spec.add(new SearchStatement(Consts.ISBN, isbn, SearchOperation.MATCH));
        }

        if (rating != null) {
            spec.add(new SearchStatement(Consts.RATING, rating, SearchOperation.GREATER_THAN_EQUAL));
        }

        if (visible != null) {
            spec.add(new SearchStatement(Consts.VISIBLE, visible, SearchOperation.EQUAL));
        }

        if (stock != null) {
            spec.add(new SearchStatement(Consts.STOCK, stock, SearchOperation.GREATER_THAN_EQUAL));
        }

        if (price != null) {
            spec.add(new SearchStatement(Consts.PRICE, price, SearchOperation.GREATER_THAN_EQUAL));
        }

        return repository.findAll(spec);
    }
}
