package org.readingservice.repository;


import org.readingservice.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    Book findBookById(String id);

    Optional<Book> findByTitleAndAuthor(String title, String author);
    boolean existsById(String id);
    boolean existsByTitleIgnoreCaseAndAuthorIgnoreCase(String title, String author);

    boolean existsByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(String title, String author);

    // Tìm tất cả sách, sắp xếp theo viewCount giảm dần
    List<Book> findAllByOrderByViewCountDesc();

    // Tìm tất cả sách, sắp xếp theo ngày tạo mới nhất
    List<Book> findAllByOrderByCreatedDateDesc();
}
