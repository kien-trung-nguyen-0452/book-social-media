package org.example.searchservice.repository;


import org.example.searchservice.entity.BookIndex;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookIndexRepository extends ElasticsearchRepository<BookIndex, String>, BookIndexRepositoryCustom {
    // các phương thức khác


    List<BookIndex> findByTitleContainingIgnoreCase(String keyword);
    BookIndex findBookIndexById(String id);
    @Query("""
    {
      "bool": {
        "must": [
          #foreach($category in ${categories})
          { "term": { "categories.keyword": "$category" }}#if($foreach.hasNext),#end
          #end
        ]
      }
    }
    """)
    List<BookIndex> findBookIndicesByCategories(List<String> categories);
    List<BookIndex> findBookIndicesByTitle(String title);


}
