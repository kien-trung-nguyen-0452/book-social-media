package org.chapterservice.repository;


import org.chapterservice.dto.common.ApiResponse;
import org.chapterservice.dto.request.HistoryRecordRequest;
import org.chapterservice.dto.response.HistoryRecordResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "reading-history-service")
public interface ReadingHistoryClient {
    @PostMapping("/internal/save-record")
    ApiResponse<HistoryRecordResponse> saveRecord (@RequestBody HistoryRecordRequest request);
}
