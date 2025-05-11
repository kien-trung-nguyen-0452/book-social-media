package org.chapterservice.controller;

import lombok.RequiredArgsConstructor;
import org.chapterservice.dto.common.ApiResponse;
import org.chapterservice.dto.request.ChapterRequest;
import org.chapterservice.dto.response.ChapterResponse;
import org.chapterservice.service.ChapterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chapters")
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;

    @PostMapping
    public ApiResponse<ChapterResponse> createChapter(@RequestBody ChapterRequest request) {
        return ApiResponse.<ChapterResponse>builder()
                .data(chapterService.createChapter(request))
                .message("Chapter created")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ChapterResponse> getChapterById(@PathVariable String id) {
        return ApiResponse.<ChapterResponse>builder()
                .data(chapterService.getChapterById(id))
                .message("Chapter fetched")
                .build();
    }

    @GetMapping("/book/{bookId}")
    public ApiResponse<List<ChapterResponse>> getChaptersByBookId(@PathVariable Long bookId) {
        return ApiResponse.<List<ChapterResponse>>builder()
                .data(chapterService.getChaptersByBookId(bookId))
                .message("Chapters fetched")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ChapterResponse> updateChapter(@PathVariable String id, @RequestBody ChapterRequest request) {
        return ApiResponse.<ChapterResponse>builder()
                .data(chapterService.updateChapter(id, request))
                .message("Chapter updated")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteChapter(@PathVariable String id) {
        chapterService.deleteChapter(id);
        return ApiResponse.<Void>builder()
                .message("Chapter deleted")
                .build();
    }

    @GetMapping("/book/{bookId}/last")
    public ApiResponse<ChapterResponse> getLastChapterByBookId(@PathVariable Long bookId) {
        return ApiResponse.<ChapterResponse>builder()
                .data(chapterService.getLastChapterByBookId(bookId))
                .message("Last chapter fetched")
                .build();
    }

    @GetMapping("/book/{bookId}/number/{chapterNumber}")
    public ApiResponse<ChapterResponse> getChapterByBookIdAndNumber(
            @PathVariable Long bookId,
            @PathVariable int chapterNumber
    ) {
        return ApiResponse.<ChapterResponse>builder()
                .data(chapterService.getChapterByBookIdAndNumber(bookId, chapterNumber))
                .message("Chapter fetched by number")
                .build();
    }

    @GetMapping("/book/{bookId}/count")
    public ApiResponse<Long> countChaptersByBookId(@PathVariable Long bookId) {
        return ApiResponse.<Long>builder()
                .data(chapterService.countChaptersByBookId(bookId))
                .message("Counted chapters")
                .build();
    }

    @DeleteMapping("/book/{bookId}")
    public ApiResponse<Void> deleteChaptersByBookId(@PathVariable Long bookId) {
        chapterService.deleteChaptersByBookId(bookId);
        return ApiResponse.<Void>builder()
                .message("All chapters deleted")
                .build();
    }
}
