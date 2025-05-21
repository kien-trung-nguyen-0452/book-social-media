package org.chapterservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.chapterservice.dto.common.ApiResponse;
import org.chapterservice.dto.request.ChapterRequest;
import org.chapterservice.dto.response.ChapterResponse;
import org.chapterservice.service.ChapterService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)

public class InternalChapterController {
    ChapterService chapterService;

    @PostMapping("{bookId}/add")
    public ApiResponse<ChapterResponse> createChapter(@PathVariable("bookId") String bookId,
                                                      @RequestBody ChapterRequest request) {
        ChapterResponse response = chapterService.createChapter(bookId, request);
        return ApiResponse.<ChapterResponse>builder()
                .data(response)
                .message("Chapter created")
                .build();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<ChapterResponse> updateChapter(@PathVariable String id, @RequestBody ChapterRequest request) {
        return ApiResponse.<ChapterResponse>builder()
                .data(chapterService.updateChapter(id, request))
                .message("Chapter updated")
                .build();
    }

    @DeleteMapping("delete/book/{bookId}")
    public ApiResponse<Void> deleteChaptersByBookId(@PathVariable String bookId) {
        chapterService.deleteChaptersByBookId(bookId);
        return ApiResponse.<Void>builder()
                .message("All chapters deleted")
                .build();
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<Void> deleteChapter(@PathVariable String id) {
        chapterService.deleteChapter(id);
        return ApiResponse.<Void>builder()
                .message("Chapter deleted")
                .build();
    }
}
