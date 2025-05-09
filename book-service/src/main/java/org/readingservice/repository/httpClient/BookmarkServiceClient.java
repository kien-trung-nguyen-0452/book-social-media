package org.readingservice.repository.httpClient;

import org.readingservice.repository.httpClient.dto.bookmark.BookmarkRequest;
import org.readingservice.repository.httpClient.dto.bookmark.BookmarkResponse;
import org.readingservice.dto.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "bookmark-service")
public interface BookmarkServiceClient {

    @PostMapping("/bookmarks")
    ApiResponse<BookmarkResponse> createBookmark(@RequestBody BookmarkRequest request);

    @DeleteMapping("/bookmarks/{id}")
    ApiResponse<Void> deleteBookmark(@PathVariable("id") Long id);

    @GetMapping("/bookmarks/user/{userId}")
    ApiResponse<List<BookmarkResponse>> getBookmarksByUser(@PathVariable("userId") Long userId);

    @GetMapping("/bookmarks/check")
    ApiResponse<Boolean> isBookmarked(
            @RequestParam("userId") Long userId,
            @RequestParam("bookId") Long bookId,
            @RequestParam(value = "chapterId", required = false) Long chapterId
    );
}
