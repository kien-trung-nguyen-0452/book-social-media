package org.example.readinghistoryservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.readinghistoryservice.dto.common.ApiResponse;
import org.example.readinghistoryservice.dto.response.RecordResponse;
import org.example.readinghistoryservice.service.HistoryRecordingService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/record")
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class HistoryRecordController {
    HistoryRecordingService historyRecordingService;

    @GetMapping("/{userId}")
    public ApiResponse<Page<RecordResponse>> getRecordByUserId (@PathVariable String userId,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size){
        return ApiResponse.<Page<RecordResponse>>builder()
                .code(1000)
                .result(historyRecordingService.getRecordsByUserId(userId, page, size))
                .build();

    }
}
