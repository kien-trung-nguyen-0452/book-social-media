package org.example.searchservice.repository;


import org.example.searchservice.entity.BookIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookIndexRepository extends ElasticsearchRepository<BookIndex, String> {
    List<BookIndex> findByTitleContainingIgnoreCase(String keyword);
    BookIndex findBookIndexById(String id);

}
