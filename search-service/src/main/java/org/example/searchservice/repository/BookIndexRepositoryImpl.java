package org.example.searchservice.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;

import org.example.searchservice.entity.BookIndex;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BookIndexRepositoryImpl implements BookIndexRepositoryCustom {

    private final ElasticsearchClient elasticsearchClient;

    public BookIndexRepositoryImpl(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    @Override
    public List<BookIndex> autocompleteTitle(String prefix) {
        try {
            SearchResponse<BookIndex> response = elasticsearchClient.search(s -> s
                    .index("bookindex")  // tên index trong Elasticsearch
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
