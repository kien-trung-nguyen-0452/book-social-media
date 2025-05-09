package org.bookmarkservice.controller;

import lombok.RequiredArgsConstructor;
import org.bookmarkservice.dto.request.BookmarkRequest;
import org.bookmarkservice.dto.common.ApiResponse;
import org.bookmarkservice.dto.response.BookmarkResponse;
import org.bookmarkservice.service.BookmarkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    public ApiResponse<BookmarkResponse> createBookmark(@RequestBody BookmarkRequest request) {
        BookmarkResponse response = bookmarkService.createBookmark(request);
        return ApiResponse.<BookmarkResponse>builder()
                .data(response)
                .message("Bookmark created successfully")
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<BookmarkResponse>> getBookmarksByUser(@PathVariable Long userId) {
        List<BookmarkResponse> response = bookmarkService.getBookmarksByUserId(userId);
        return ApiResponse.<List<BookmarkResponse>>builder()
                .data(response)
                .message("Bookmarks retrieved")
                .build();
    }

    @DeleteMapping
    public ApiResponse<Void> deleteBookmark(@RequestBody BookmarkRequest request) {
        bookmarkService.deleteBookmark(request);
        return ApiResponse.<Void>builder()
                .data(null)
                .message("Bookmark deleted")
                .build();
    }

    @GetMapping("/exists")
    public ApiResponse<Boolean> existsBookmark(
            @RequestParam Long userId,
            @RequestParam Long bookId
    ) {
        Boolean exists = bookmarkService.existsBookmark(userId, bookId);
        return ApiResponse.<Boolean>builder()
                .data(exists)
                .message("Bookmark exists")
                .build();
    }
}
