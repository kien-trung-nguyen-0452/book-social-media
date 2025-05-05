package org.bookmarkservice.controller;

import lombok.RequiredArgsConstructor;
import org.bookmarkservice.dto.request.BookmarkRequest;
import org.bookmarkservice.dto.common.ApiResponse;
import org.bookmarkservice.dto.response.BookmarkResponse;
import org.bookmarkservice.service.BookmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookmarkResponse>> createBookmark(@RequestBody BookmarkRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                bookmarkService.createBookmark(request),
                "Bookmark created successfully", 200));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<BookmarkResponse>>> getBookmarksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(new ApiResponse<>(
                bookmarkService.getBookmarksByUserId(userId),
                "Bookmarks retrieved", 200));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteBookmark(@RequestBody BookmarkRequest request) {
        bookmarkService.deleteBookmark(request);
        return ResponseEntity.ok(new ApiResponse<>(null, "Bookmark deleted", 200));
    }

    @GetMapping("/exists")
    public ResponseEntity<ApiResponse<Boolean>> existsBookmark(
            @RequestParam Long userId,
            @RequestParam Long bookId
    ) {
        return ResponseEntity.ok(new ApiResponse<>(
                bookmarkService.existsBookmark(userId, bookId),
                "Bookmark exists", 200));
    }
}
