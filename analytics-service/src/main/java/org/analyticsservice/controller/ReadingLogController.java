package org.analyticsservice.controller;

import lombok.RequiredArgsConstructor;
import org.analyticsservice.dto.common.ApiResponse;
import org.analyticsservice.dto.request.ReadingLogRequest;
import org.analyticsservice.dto.response.ReadingLogResponse;
import org.analyticsservice.service.ReadingLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analysis/logs")
@RequiredArgsConstructor
public class ReadingLogController {

    private final ReadingLogService readingLogService;

    @PostMapping
    public ApiResponse<ReadingLogResponse> createLog(@RequestBody ReadingLogRequest request) {
        return ApiResponse.<ReadingLogResponse>builder()
                .data(readingLogService.saveReadingLog(request))
                .message("Reading log saved successfully")
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<ReadingLogResponse>> getLogsByUser(@PathVariable Long userId) {
        return ApiResponse.<List<ReadingLogResponse>>builder()
                .data(readingLogService.getLogsByUser(userId))
                .message("Fetched logs successfully")
                .build();
    }

    @GetMapping("/book/{bookId}")
    public ApiResponse<List<ReadingLogResponse>> getLogsByBook(@PathVariable Long bookId) {
        return ApiResponse.<List<ReadingLogResponse>>builder()
                .data(readingLogService.getLogsByBook(bookId))
                .message("Fetched logs by book successfully")
                .build();
    }
}
