package com.unir.back_end_inventory_books_elasticsearch.data;

import java.util.List;
import java.util.Optional;

import com.unir.back_end_inventory_books_elasticsearch.data.model.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookRepository extends ElasticsearchRepository<Book, String> {

    List<Book> findByTitle(String title);

    Optional<Book> findById(String id);

    Book save(Book book);

    void delete(Book book);

    List<Book> findAll();
}
