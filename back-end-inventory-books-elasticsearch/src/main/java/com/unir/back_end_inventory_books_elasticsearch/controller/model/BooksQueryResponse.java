package com.unir.back_end_inventory_books_elasticsearch.controller.model;

import com.unir.back_end_inventory_books_elasticsearch.data.model.Book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BooksQueryResponse {

    private List<Book> books;
//    private List<AggregationDetails> aggs;
    private Map<String, List<AggregationDetails>> aggs;
}
