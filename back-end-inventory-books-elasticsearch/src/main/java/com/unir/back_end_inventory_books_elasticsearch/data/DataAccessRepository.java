package com.unir.back_end_inventory_books_elasticsearch.data;

import java.util.*;

import com.unir.back_end_inventory_books_elasticsearch.controller.model.AggregationDetails;
import com.unir.back_end_inventory_books_elasticsearch.controller.model.BooksQueryResponse;
import com.unir.back_end_inventory_books_elasticsearch.data.model.Book;
import com.unir.back_end_inventory_books_elasticsearch.utils.Consts;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder.Type;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.ParsedRange;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DataAccessRepository {

    @Value("${server.fullAddress}")
    private String serverFullAddress;

    // Esta clase (y bean) es la unica que usan directamente los servicios para
    // acceder a los datos.
    private final BookRepository bookRepository;
    private final ElasticsearchOperations elasticClient;

    private final String[] authorSearchFields = {"author", "author._2gram", "author._3gram"};

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Boolean delete(Book book) {
        bookRepository.delete(book);
        return Boolean.TRUE;
    }

    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    @SneakyThrows
    public BooksQueryResponse findBooks(String title, String author, String category, Boolean visible) {

        BoolQueryBuilder querySpec = QueryBuilders.boolQuery();

        if (!StringUtils.isEmpty(category)) {
            querySpec.must(QueryBuilders.termQuery("category", category));
        }

        if (!StringUtils.isEmpty(title)) {
            querySpec.must(QueryBuilders.matchQuery("title", title));
        }

        if (!StringUtils.isEmpty(author)) {
            querySpec.must(QueryBuilders.multiMatchQuery(author, authorSearchFields).type(Type.BOOL_PREFIX));
        }

        //Si no he recibido ningun parametro, busco todos los elementos.
        if (!querySpec.hasClauses()) {
            querySpec.must(QueryBuilders.matchAllQuery());
        }

        //Filtro implicito
        //No le pido al usuario que lo introduzca pero lo aplicamos proactivamente en todas las peticiones
        //En este caso, que los libros sean visibles (estado correcto de la entidad)
        querySpec.must(QueryBuilders.termQuery("visible", true));

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(querySpec);

        if (visible) {
            nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("Visible Aggregation").field("visible").size(1000));
            // Mas info sobre size 1000 - https://www.elastic.co/guide/en/elasticsearch/reference/7.10/search-aggregations-bucket-terms-aggregation.html#search-aggregations-bucket-terms-aggregation-size
            nativeSearchQueryBuilder.withMaxResults(0);
        }

        //Opcionalmente, podemos paginar los resultados
        //nativeSearchQueryBuilder.withPageable(PageRequest.of(0, 10));
        //Pagina 0, 10 elementos por pagina. El tam de pagina puede venir como qParam y tambien el numero de pagina

        Query query = nativeSearchQueryBuilder.build();
        SearchHits<Book> result = elasticClient.search(query, Book.class);

        List<AggregationDetails> responseAggs = new LinkedList<>();

        if (result.hasAggregations()) {
            Map<String, Aggregation> aggs = result.getAggregations().asMap();
            ParsedStringTerms categoryAgg = (ParsedStringTerms) aggs.get("Category Aggregation");

            //Componemos una URI basada en serverFullAddress y query params para cada argumento, siempre que no viniesen vacios
            String queryParams = getQueryParams(title, author, category);
            categoryAgg.getBuckets()
                    .forEach(
                            bucket -> responseAggs.add(
                                    new AggregationDetails(
                                            bucket.getKey().toString(),
                                            (int) bucket.getDocCount(),
                                            serverFullAddress + "/books?category=" + bucket.getKey() + queryParams)));
        }
        return new BooksQueryResponse(result.getSearchHits().stream().map(SearchHit::getContent).toList(), getResponseAggregations(result));
    }

    @SneakyThrows
    public BooksQueryResponse findBooks(
            List<String> ratingValues,
            List<String> categoryValues,
            List<String> priceValues,
            List<String> stockValues,
            String title,
            String author,
            String page) {

        BoolQueryBuilder querySpec = QueryBuilders.boolQuery();

        // Si el usuario ha seleccionado algun valor relacionado con la categotía, lo añadimos a la query
        if (categoryValues != null && !categoryValues.isEmpty()) {
            categoryValues.forEach(
                    category -> querySpec.must(QueryBuilders.termQuery(Consts.FIELD_CATEGORY, category))
            );
        }

        // Si el usuario ha seleccionado algun valor relacionado con el titulo, lo añadimos a la query
        if (!StringUtils.isEmpty(title)) {
            querySpec.must(QueryBuilders.matchQuery(Consts.FIELD_TITLE, title));
        }

        // Si el usuario ha seleccionado algun valor relacionado con el author, lo añadimos a la query
        if (!StringUtils.isEmpty(author)) {
            querySpec.must(QueryBuilders.matchQuery(Consts.FIELD_AUTHOR, author));
        }

        // Si el usuario ha seleccionado algun valor relacionado con el author, lo añadimos a la query
        if (!StringUtils.isEmpty(author)) {
            querySpec.must(QueryBuilders.multiMatchQuery(author, authorSearchFields).type(MultiMatchQueryBuilder.Type.BOOL_PREFIX));
        }

        // Si el usuario ha seleccionado algun valor relacionado con el rating, lo añadimos a la query
        if (ratingValues != null && !ratingValues.isEmpty()) {
            ratingValues.forEach(
                    rating -> {
                        String[] ratingRange = rating != null && rating.contains("-") ? rating.split("-") : new String[]{};

                        if (ratingRange.length == 2) {
                            if ("".equals(ratingRange[0])) {
                                querySpec.must(QueryBuilders.rangeQuery(Consts.FIELD_RATING).to(ratingRange[1]).includeUpper(false));
                            } else {
                                querySpec.must(QueryBuilders.rangeQuery(Consts.FIELD_RATING).from(ratingRange[0]).to(ratingRange[1]).includeUpper(false));
                            }
                        } if (ratingRange.length == 1) {
                            querySpec.must(QueryBuilders.rangeQuery(Consts.FIELD_RATING).from(ratingRange[0]));
                        }
                    }
            );
        }

        // Si el usuario ha seleccionado algun valor relacionado con el stock, lo añadimos a la query
        if (stockValues != null && !stockValues.isEmpty()) {
            stockValues.forEach(
                    stock -> {
                        String[] stockRange = stock != null && stock.contains("-") ? stock.split("-") : new String[]{};

                        if (stockRange.length == 2) {
                            if ("".equals(stockRange[0])) {
                                querySpec.must(QueryBuilders.rangeQuery(Consts.FIELD_STOCK).to(stockRange[1]).includeUpper(false));
                            } else {
                                querySpec.must(QueryBuilders.rangeQuery(Consts.FIELD_STOCK).from(stockRange[0]).to(stockRange[1]).includeUpper(false));
                            }
                        } if (stockRange.length == 1) {
                            querySpec.must(QueryBuilders.rangeQuery(Consts.FIELD_STOCK).from(stockRange[0]));
                        }
                    }
            );
        }

        // Si el usuario ha seleccionado algun valor relacionado con el price, lo añadimos a la query
        if (priceValues != null && !priceValues.isEmpty())
            priceValues.forEach(
                    price -> {
                        String[] salaryRange = price != null && price.contains("-") ? price.split("-") : new String[]{};

                        if (salaryRange.length == 2) {
                            if ("".equals(salaryRange[0])) {
                                querySpec.must(QueryBuilders.rangeQuery(Consts.FIELD_PRICE).to(salaryRange[1]).includeUpper(false));
                            } else {
                                querySpec.must(QueryBuilders.rangeQuery(Consts.FIELD_PRICE).from(salaryRange[0]).to(salaryRange[1]).includeUpper(false));
                            }
                        } if (salaryRange.length == 1) {
                            querySpec.must(QueryBuilders.rangeQuery(Consts.FIELD_PRICE).from(salaryRange[0]));
                        }
                    }
            );

        //Si no se ha seleccionado ningun filtro, se añade un filtro por defecto para que la query no sea vacia
        if(!querySpec.hasClauses()) {
            querySpec.must(QueryBuilders.matchAllQuery());
        }

        //Construimos la query
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(querySpec);

        //Se incluyen las Agregaciones
        //Se incluyen las agregaciones de termino para los campos category y publicationDate
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders
                .terms(Consts.AGG_KEY_TERM_CATEGORY)
                .field(Consts.FIELD_CATEGORY).size(10000));

        nativeSearchQueryBuilder.addAggregation(AggregationBuilders
                .terms(Consts.AGG_KEY_TERM_PUBLICATION_DATE)
                .field(Consts.FIELD_PUBLICATION_DATE).size(10000));

        //Se incluyen las agregaciones de rango para los campos rating, price y stock
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders
                .range(Consts.AGG_KEY_RANGE_RATING)
                .field(Consts.FIELD_RATING)
                .addUnboundedTo(Consts.AGG_KEY_RANGE_RATING_0,3.0)
                .addRange(Consts.AGG_KEY_RANGE_RATING_1, 3.0, 4.5)
                .addUnboundedFrom(Consts.AGG_KEY_RANGE_RATING_2,5.0));

        nativeSearchQueryBuilder.addAggregation(AggregationBuilders
                .range(Consts.AGG_KEY_RANGE_PRICE)
                .field(Consts.FIELD_PRICE)
                .addUnboundedTo(Consts.AGG_KEY_RANGE_PRICE_0,12.00)
                .addRange(Consts.AGG_KEY_RANGE_PRICE_1,12.00, 15.00)
                .addUnboundedFrom(Consts.AGG_KEY_RANGE_PRICE_2,15.00));

        nativeSearchQueryBuilder.addAggregation(AggregationBuilders
                .range(Consts.AGG_KEY_RANGE_STOCK)
                .field(Consts.FIELD_STOCK)
                .addUnboundedTo(Consts.AGG_KEY_RANGE_STOCK_0,30)
                .addRange(Consts.AGG_KEY_RANGE_STOCK_1,30, 60)
                .addUnboundedFrom(Consts.AGG_KEY_RANGE_STOCK_2,90));

        //Se establece un maximo de 5 resultados, va acorde con el tamaño de la pagina
        nativeSearchQueryBuilder.withMaxResults(5);

        //Podemos paginar los resultados en base a la pagina que nos llega como parametro
        //El tamaño de la pagina es de 5 elementos (pero el propio llamante puede cambiarlo si se habilita en la API)
        int pageInt = Integer.parseInt(page);
        if (pageInt >= 0) {
            nativeSearchQueryBuilder.withPageable(PageRequest.of(pageInt,5));
        }

        //Se construye la query
        Query query = nativeSearchQueryBuilder.build();
        // Se realiza la busqueda
        SearchHits<Book> result = elasticClient.search(query, Book.class);
        return new BooksQueryResponse(getResponseBooks(result), getResponseAggregations(result));
    }

    private List<Book> getResponseBooks(SearchHits<Book> result) {
        return result.getSearchHits().stream().map(SearchHit::getContent).toList();
    }

    private Map<String, List<AggregationDetails>> getResponseAggregations(SearchHits<Book> result) {

        //Mapa de detalles de agregaciones
        Map<String, List<AggregationDetails>> responseAggregations = new HashMap<>();

        //Recorremos las agregaciones
        if (result.hasAggregations()) {
            Map<String, Aggregation> aggs = result.getAggregations().asMap();

            //Recorremos las agregaciones
            aggs.forEach((key, value) -> {

                //Si no existe la clave en el mapa, la creamos
                if(!responseAggregations.containsKey(key)) {
                    responseAggregations.put(key, new LinkedList<>());
                }

                //Si la agregacion es de tipo termino, recorremos los buckets
                if (value instanceof ParsedStringTerms parsedStringTerms) {
                    parsedStringTerms.getBuckets().forEach(bucket -> {
                        responseAggregations.get(key).add(new AggregationDetails(bucket.getKey().toString(), (int) bucket.getDocCount(), key));
                    });
                }

                //Si la agregacion es de tipo rango, recorremos tambien los buckets
                if (value instanceof ParsedRange parsedRange) {
                    parsedRange.getBuckets().forEach(bucket -> {
                        responseAggregations.get(key).add(new AggregationDetails(bucket.getKeyAsString(), (int) bucket.getDocCount(), key));
                    });
                }
            });
        }
        return responseAggregations;
    }

    /**
     * Componemos una URI basada en serverFullAddress y query params para cada argumento, siempre que no viniesen vacios
     *
     * @param title         - titulo del libro
     * @param author        - author del libro
     * @param category      - categoría del libro
     * @return
     */
    private String getQueryParams(String title, String author, String category) {
        String queryParams = (StringUtils.isEmpty(title) ? "" : "&title=" + title)
                + (StringUtils.isEmpty(author) ? "" : "&author=" + author)
                + (StringUtils.isEmpty(category) ? "" : "&category=" + category);
        // Eliminamos el ultimo & si existe
        return queryParams.endsWith("&") ? queryParams.substring(0, queryParams.length() - 1) : queryParams;
    }
}
