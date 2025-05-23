package org.chapterservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.chapterservice.dto.common.ApiResponse;
import org.chapterservice.dto.response.ChapterResponse;
import org.chapterservice.service.ChapterService;
import org.chapterservice.service.ChapterServiceImpl;
import org.chapterservice.service.ViewCountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chapters")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Tag(name = "External Chapter APIs", description = "Endpoints for fetching chapter data")
public class ChapterController {

    ChapterService chapterService;
    ViewCountService viewCountService;

    @GetMapping("/{bookId}/{chapterId}")
    @Operation(summary = "Get chapter by ID", description = "Fetch a chapter by chapterId and bookId")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Chapter fetched successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Chapter not found")
    })
    public ApiResponse<ChapterResponse> getChapterById(@PathVariable String bookId, @PathVariable String chapterId) {
        var result = chapterService.getChapterById(chapterId);
        viewCountService.incrementViewCount(bookId); // Record view count
        return ApiResponse.<ChapterResponse>builder()
                .data(result)
                .message("Chapter fetched")
                .build();
    }

    @GetMapping("/book/{bookId}")
    @Operation(summary = "Get all chapters of a book", description = "Fetch all chapters by bookId")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Chapters fetched successfully")
    })
    public ApiResponse<List<ChapterResponse>> getChaptersByBookId(@PathVariable String bookId) {
        return ApiResponse.<List<ChapterResponse>>builder()
                .data(chapterService.getChaptersByBookId(bookId))
                .message("Chapters fetched")
                .build();
    }

    @GetMapping("/book/{bookId}/last")
    @Operation(summary = "Get last chapter", description = "Fetch the last (latest) chapter of a book")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Last chapter fetched successfully")
    })
    public ApiResponse<ChapterResponse> getLastChapterByBookId(@PathVariable String bookId) {
        return ApiResponse.<ChapterResponse>builder()
                .data(chapterService.getLastChapterByBookId(bookId))
                .message("Last chapter fetched")
                .build();
    }

    @GetMapping("/book/{bookId}/number/{chapterNumber}")
    @Operation(summary = "Get chapter by number", description = "Fetch a chapter of a book by its number")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Chapter fetched by number successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Chapter not found")
    })
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
    @Operation(summary = "Count chapters", description = "Count the number of chapters in a book")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Chapter count retrieved successfully")
    })
    public ApiResponse<Long> countChaptersByBookId(@PathVariable String bookId) {
        return ApiResponse.<Long>builder()
                .data(chapterService.countChaptersByBookId(bookId))
                .message("Counted chapters")
                .build();
    }
}
