package com.unir.back_end_inventory_books_elasticsearch.controller;

import com.unir.back_end_inventory_books_elasticsearch.controller.model.BooksQueryResponse;
import com.unir.back_end_inventory_books_elasticsearch.service.BooksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/facets/books")
public class FacetsController {

    private final BooksService service;

    // GET /facets/books
    @GetMapping
    public ResponseEntity<BooksQueryResponse> getProducts(
            @RequestParam(required = false) List<String> ratingValues,
            @RequestParam(required = false) List<String> categoryValues,
            @RequestParam(required = false) List<String> priceValues,
            @RequestParam(required = false) List<String> stockValues,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false, defaultValue = "0") String page) {

        BooksQueryResponse response = service.findBooks(
                ratingValues,
                categoryValues,
                priceValues,
                stockValues,
                title,
                author,
                page);
        return ResponseEntity.ok(response);
    }
}
