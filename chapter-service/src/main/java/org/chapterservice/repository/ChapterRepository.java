package org.chapterservice.repository;

import org.chapterservice.entity.Chapter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChapterRepository extends MongoRepository<Chapter, String> {

    List<Chapter> findByBookIdOrderByChapterNumberAsc(String bookId);

    Optional<Chapter> findByBookIdAndChapterNumber(String bookId, Integer chapterNumber);

    long countByBookId(String bookId); // Mongo dùng `long` cho count

    Optional<Chapter> findFirstByBookIdOrderByChapterNumberDesc(String bookId);

    void deleteByBookId(String bookId);

    boolean existsChaptersByBookId(String bookId);
    // Kiểm tra chapter trùng title hoặc số chương

    boolean existsByBookIdAndTitleAndChapterNumber(String bookId, String title, int chapterNumber);


}
