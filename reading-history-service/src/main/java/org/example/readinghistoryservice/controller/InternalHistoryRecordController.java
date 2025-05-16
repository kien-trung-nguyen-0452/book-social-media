package org.example.readinghistoryservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.readinghistoryservice.dto.common.ApiResponse;
import org.example.readinghistoryservice.dto.request.RecordRequest;
import org.example.readinghistoryservice.dto.response.RecordResponse;
import org.example.readinghistoryservice.service.HistoryRecordingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class InternalHistoryRecordController {
    HistoryRecordingService historyRecordingService;

    @PostMapping("/save-record")
    public ApiResponse<RecordResponse> saveRecord(@RequestBody RecordRequest request){
        return ApiResponse.<RecordResponse>builder()
                .code(1000)
                .result(historyRecordingService.recordHistory(request))
                .build();
    }
}
