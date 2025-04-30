package org.chapterservice.repository;

import org.chapterservice.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findByBookIdOrderByChapterNumberAsc(Long bookId);
    Optional<Chapter> findByBookIdAndChapterNumber(Long bookId, Integer chapterNumber);
    int countByBookId(Long bookId);
    List<Chapter> findByTitleContainingIgnoreCase(String title);
    Optional<Chapter> findFirstByBookIdOrderByChapterNumberDesc(Long bookId);
    void deleteByBookId(Long bookId);
}
