package org.readingservice.client;

import org.readingservice.client.dto.bookmark.BookmarkRequest;
import org.readingservice.client.dto.bookmark.BookmarkResponse;
import org.readingservice.dto.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "bookmark-service", url = "${service.bookmark.base-url}")
public interface BookmarkServiceClient {

    @PostMapping("/api/bookmarks")
    ApiResponse<BookmarkResponse> createBookmark(@RequestBody BookmarkRequest request);

    @DeleteMapping("/api/bookmarks/{id}")
    ApiResponse<Void> deleteBookmark(@PathVariable("id") Long id);

    @GetMapping("/api/bookmarks/user/{userId}")
    ApiResponse<List<BookmarkResponse>> getBookmarksByUser(@PathVariable("userId") Long userId);

    @GetMapping("/api/bookmarks/check")
    ApiResponse<Boolean> isBookmarked(
            @RequestParam("userId") Long userId,
            @RequestParam("bookId") Long bookId,
            @RequestParam(value = "chapterId", required = false) Long chapterId
    );
}
