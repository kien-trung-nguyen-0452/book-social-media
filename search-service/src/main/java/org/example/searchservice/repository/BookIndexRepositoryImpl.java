package org.example.searchservice.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.entity.BookIndex;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Repository
public class BookIndexRepositoryImpl implements BookIndexRepositoryCustom {
    private final ObjectMapper objectMapper;
    private final ElasticsearchClient elasticsearchClient;

    public BookIndexRepositoryImpl(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // optional
    }
    @Override
    public List<BookIndex> findAll() {
        try {
            SearchResponse<BookIndex> response = elasticsearchClient.search(s -> s
                    .index("book-index")
                    .query(q -> q
                            .matchAll(m -> m)
                    ), BookIndex.class);

            return response.hits().hits().stream()
                    .map(Hit::source)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            log.error("Elasticsearch query failed", e);
            return Collections.emptyList();
        }
    }
    @Override
    public List<BookIndex> findBookIndicesByAuthor(String author) {
        try {
            if (author == null || author.isEmpty()) {
                return Collections.emptyList();
            }

            SearchResponse<BookIndex> response = elasticsearchClient.search(s -> s
                    .index("book-index")
                    .query(q -> q
                            .match(m -> m
                                    .field("author")
                                    .query(author)
                            )
                    ), BookIndex.class);

            return response.hits().hits().stream()
                    .map(Hit::source)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            log.error("Elasticsearch query by author failed", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<BookIndex> findBookIndicesByCategories(List<String> categories) {
        try {
            if (categories == null || categories.isEmpty()) {
                return Collections.emptyList();
            }

            SearchResponse<BookIndex> response = elasticsearchClient.search(s -> s
                    .index("book-index")
                    .query(q -> q
                            .bool(b -> b
                                    .must(categories.stream()
                                            .map(cat -> Query.of(q2 -> q2
                                                    .term(t -> t
                                                            .field("categories")
                                                            .value(FieldValue.of(cat))
                                                    )
                                            )).collect(Collectors.toList())
                                    )
                            )
                    ), BookIndex.class);

            return response.hits().hits().stream()
                    .map(Hit::source)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            log.error("Elasticsearch query failed", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<BookIndex> autocompleteTitle(String prefix) {
        try {
            SearchResponse<BookIndex> response = elasticsearchClient.search(s -> s
                    .index("book-index")  // tên index trong Elasticsearch
                    .query(q -> q
                            .multiMatch(m -> m
                                    .query(prefix)
                                    .type(TextQueryType.BoolPrefix)
                                    .fields("title", "title._2gram", "title._3gram")
                            )
                    ), BookIndex.class);

            return response.hits().hits().stream()
                    .map(Hit::source)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
