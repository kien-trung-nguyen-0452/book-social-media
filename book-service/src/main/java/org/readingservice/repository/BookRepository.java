package org.readingservice.repository;


import org.readingservice.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    Book findBookById(String id);

    Optional<Book> findByTitleAndAuthor(String title, String author);
    boolean existsById(String id);
}
