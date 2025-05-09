package org.readingservice.repository.httpClient;

import org.readingservice.dto.common.ApiResponse;
import org.readingservice.repository.httpClient.dto.chapter.ChapterResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "chapter-service")
public interface ChapterServiceClient {

    @GetMapping("/chapters/book/{bookId}")
    ApiResponse<List<ChapterResponse>> getChaptersByBookId(@PathVariable("bookId") Long bookId);

    @GetMapping("/chapters/book/{bookId}/last")
    ApiResponse<ChapterResponse> getLastChapterByBookId(@PathVariable("bookId") Long bookId);

    @GetMapping("/chapters/{id}")
    ApiResponse<ChapterResponse> getChapterById(@PathVariable("id") Long id);

    @GetMapping("/chapters/book/{bookId}/number/{chapterNumber}")
    ApiResponse<ChapterResponse> getChapterByBookIdAndNumber(
            @PathVariable("bookId") Long bookId,
            @PathVariable("chapterNumber") int chapterNumber
    );
}
