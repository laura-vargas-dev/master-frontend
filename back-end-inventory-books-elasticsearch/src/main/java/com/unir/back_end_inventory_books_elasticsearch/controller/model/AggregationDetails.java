package com.unir.back_end_inventory_books_elasticsearch.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AggregationDetails {

    private String key;
    private Integer count;
    private String uri;
}
