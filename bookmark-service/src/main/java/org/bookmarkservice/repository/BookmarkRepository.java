package org.bookmarkservice.repository;

import org.bookmarkservice.entity.Bookmark;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends MongoRepository<Bookmark, String> {
    List<Bookmark> findByUserId(Long userId);
    boolean existsByUserIdAndBookId(Long userId, Long bookId);
    Optional<Bookmark> findByUserIdAndBookId(Long userId, Long bookId);
    void deleteByUserIdAndBookId(Long userId, Long bookId);
}
