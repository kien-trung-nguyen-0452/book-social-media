package org.readingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "chapter-service", url = "${chapter.service.url}")
public interface ChapterClient {

    @GetMapping("/api/chapters/{id}")
    ChapterResponse getChapterById(@PathVariable("id") Long id);

    @GetMapping("/api/chapters/book/{bookId}")
    List<ChapterResponse> getChaptersByBookId(@PathVariable("bookId") Long bookId);

    @GetMapping("/api/chapters/book/{bookId}/last")
    ChapterResponse getLastChapterByBookId(@PathVariable("bookId") Long bookId);

    @GetMapping("/api/chapters/book/{bookId}/number/{chapterNumber}")
    ChapterResponse getChapterByBookIdAndNumber(@PathVariable("bookId") Long bookId,
                                                @PathVariable("chapterNumber") int chapterNumber);

    @GetMapping("/api/chapters/book/{bookId}/count")
    Long countChaptersByBookId(@PathVariable("bookId") Long bookId);

    @DeleteMapping("/api/chapters/book/{bookId}")
    void deleteChaptersByBookId(@PathVariable("bookId") Long bookId);
}
