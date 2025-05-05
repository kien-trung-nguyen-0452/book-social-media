package org.analyticsservice.controller;


import lombok.RequiredArgsConstructor;
import org.analyticsservice.dto.common.ApiResponse;
import org.analyticsservice.dto.request.ReadingLogRequest;
import org.analyticsservice.dto.response.ReadingLogResponse;
import org.analyticsservice.service.ReadingLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analysis/logs")
@RequiredArgsConstructor
public class ReadingLogController {

    private final ReadingLogService readingLogService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReadingLogResponse>> createLog(@RequestBody ReadingLogRequest request) {
        ReadingLogResponse response = readingLogService.saveReadingLog(request);
        return new ResponseEntity<>(
                new ApiResponse<>(response, "Reading log saved successfully", HttpStatus.CREATED.value()),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<ReadingLogResponse>>> getLogsByUser(@PathVariable Long userId) {
        List<ReadingLogResponse> logs = readingLogService.getLogsByUser(userId);
        return ResponseEntity.ok(new ApiResponse<>(logs, "Fetched logs successfully", HttpStatus.OK.value()));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<ApiResponse<List<ReadingLogResponse>>> getLogsByBook(@PathVariable Long bookId) {
        List<ReadingLogResponse> logs = readingLogService.getLogsByBook(bookId);
        return ResponseEntity.ok(new ApiResponse<>(logs, "Fetched logs by book successfully", HttpStatus.OK.value()));
    }
}

