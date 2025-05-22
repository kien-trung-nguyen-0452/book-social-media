package org.example.searchservice.repository;

import org.example.searchservice.entity.BookIndex;

import java.util.List;

public interface BookIndexRepositoryCustom {
    List<BookIndex> autocompleteTitle(String prefix);
    List<BookIndex> findBookIndicesByCategories(List<String> categories);
    List<BookIndex> findAll();
    List<BookIndex> findBookIndicesByAuthor(String author);
}
