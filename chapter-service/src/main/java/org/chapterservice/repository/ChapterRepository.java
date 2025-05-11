package org.chapterservice.repository;

import org.chapterservice.entity.Chapter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChapterRepository extends MongoRepository<Chapter, String> {

    List<Chapter> findByBookIdOrderByChapterNumberAsc(Long bookId);

    Optional<Chapter> findByBookIdAndChapterNumber(Long bookId, Integer chapterNumber);

    long countByBookId(Long bookId); // Mongo dùng `long` cho count

    List<Chapter> findByTitleContainingIgnoreCase(String title);

    Optional<Chapter> findFirstByBookIdOrderByChapterNumberDesc(Long bookId);

    void deleteByBookId(Long bookId);
}
