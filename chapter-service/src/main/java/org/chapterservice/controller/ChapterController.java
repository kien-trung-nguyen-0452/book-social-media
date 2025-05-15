package org.chapterservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.chapterservice.dto.common.ApiResponse;
import org.chapterservice.dto.request.ChapterRequest;
import org.chapterservice.dto.response.ChapterResponse;
import org.chapterservice.service.ChapterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chapters")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ChapterController {

    ChapterService chapterService;

    @GetMapping("/{id}")
    public ApiResponse<ChapterResponse> getChapterById(@PathVariable String id) {
        return ApiResponse.<ChapterResponse>builder()
                .data(chapterService.getChapterById(id))
                .message("Chapter fetched")
                .build();
    }

    @GetMapping("/book/{bookId}")
    public ApiResponse<List<ChapterResponse>> getChaptersByBookId(@PathVariable String bookId) {
        return ApiResponse.<List<ChapterResponse>>builder()
                .data(chapterService.getChaptersByBookId(bookId))
                .message("Chapters fetched")
                .build();
    }


    @GetMapping("/book/{bookId}/last")
    public ApiResponse<ChapterResponse> getLastChapterByBookId(@PathVariable String bookId) {
        return ApiResponse.<ChapterResponse>builder()
                .data(chapterService.getLastChapterByBookId(bookId))
                .message("Last chapter fetched")
                .build();
    }

    @GetMapping("/book/{bookId}/number/{chapterNumber}")
    public ApiResponse<ChapterResponse> getChapterByBookIdAndNumber(
            @PathVariable String bookId,
            @PathVariable int chapterNumber
    ) {
        return ApiResponse.<ChapterResponse>builder()
                .data(chapterService.getChapterByBookIdAndNumber(bookId, chapterNumber))
                .message("Chapter fetched by number")
                .build();
    }

    @GetMapping("/book/{bookId}/count")
    public ApiResponse<Long> countChaptersByBookId(@PathVariable String bookId) {
        return ApiResponse.<Long>builder()
                .data(chapterService.countChaptersByBookId(bookId))
                .message("Counted chapters")
                .build();
    }


}