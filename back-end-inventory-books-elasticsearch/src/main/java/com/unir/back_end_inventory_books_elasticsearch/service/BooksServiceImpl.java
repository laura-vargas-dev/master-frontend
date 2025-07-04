package com.unir.back_end_inventory_books_elasticsearch.service;

import com.unir.back_end_inventory_books_elasticsearch.controller.model.BooksQueryResponse;
import com.unir.back_end_inventory_books_elasticsearch.controller.model.CreateBookRequest;
import com.unir.back_end_inventory_books_elasticsearch.data.DataAccessRepository;
import com.unir.back_end_inventory_books_elasticsearch.data.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {

    private final DataAccessRepository repository;

    @Override
    public BooksQueryResponse getBooks(String title, String author, String category, Boolean visible) {
        //Ahora por defecto solo devolvera libros visibles
        return repository.findBooks(title, author, category, visible);
    }

    @Override
    public BooksQueryResponse findBooks(
            List<String> ratingValues,
            List<String> categoryValues,
            List<String> priceValues,
            List<String> stockValues,
            String title,
            String author,
            String page) {

        return repository.findBooks(
                ratingValues,
                categoryValues,
                priceValues,
                stockValues,
                title,
                author,
                page);
    }

    @Override
    public Book getBook(String bookId) {
        return repository.findById(bookId).orElse(null);
    }

    @Override
    public Boolean removeBook(String bookId) {

        Book book = repository.findById(bookId).orElse(null);
        if (book != null) {
            repository.delete(book);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Book createBook(CreateBookRequest request) {

        if (request != null && StringUtils.hasLength(request.getId().trim())
                && StringUtils.hasLength(request.getTitle().trim())
                && StringUtils.hasLength(request.getAuthor().trim())
                && request.getPublicationDate() != null
                && StringUtils.hasLength(request.getCategory().trim())
                && StringUtils.hasLength(request.getIsbn().trim())
                && request.getRating() != null && request.getVisible() != null
                && request.getStock() != null && request.getPrice() != null
                && StringUtils.hasLength(request.getImgUrl().trim())
        ) {

            Book product = Book.builder().title(request.getTitle()).author(request.getAuthor())
                    .publicationDate(request.getPublicationDate()).category(request.getCategory())
                    .isbn(request.getIsbn()).rating(request.getRating()).stock(request.getStock())
                    .price(request.getPrice()).imgUrl(request.getImgUrl()).build();

            return repository.save(product);
        } else {
            return null;
        }
    }

}
