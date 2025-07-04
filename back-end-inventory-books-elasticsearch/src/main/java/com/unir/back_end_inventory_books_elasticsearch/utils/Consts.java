package com.unir.back_end_inventory_books_elasticsearch.utils;

public class Consts {

    private Consts() {
        throw new IllegalStateException("Utility class");
    }

    //Nombres de campos
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_AUTHOR = "author";
    public static final String FIELD_PUBLICATION_DATE = "publicationDate";
    public static final String FIELD_CATEGORY = "category";
    public static final String FIELD_RATING = "rating";
    public static final String FIELD_STOCK = "stock";
    public static final String FIELD_PRICE = "price";

    //Nombres de agregaciones
    public static final String AGG_KEY_RANGE_RATING = "ratingValues";
    public static final String AGG_KEY_RANGE_RATING_0 = "-3.0";
    public static final String AGG_KEY_RANGE_RATING_1 = "3.0-4.5";
    public static final String AGG_KEY_RANGE_RATING_2 = "5.0-";

    public static final String AGG_KEY_RANGE_PRICE = "priceValues";
    public static final String AGG_KEY_RANGE_PRICE_0 = "-12.00";
    public static final String AGG_KEY_RANGE_PRICE_1 = "12.00-15.00";
    public static final String AGG_KEY_RANGE_PRICE_2 = "15.00-";

    public static final String AGG_KEY_RANGE_STOCK = "stockValues";
    public static final String AGG_KEY_RANGE_STOCK_0 = "-30";
    public static final String AGG_KEY_RANGE_STOCK_1 = "30-60";
    public static final String AGG_KEY_RANGE_STOCK_2 = "90-";

    public static final String AGG_KEY_TERM_CATEGORY = "categoryValues";

    public static final String AGG_KEY_TERM_PUBLICATION_DATE = "publicationDateValues";
}
