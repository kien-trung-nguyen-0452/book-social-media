package org.example.userprofileservice.repository;


import org.example.userprofileservice.dto.ApiResponse;
import org.example.userprofileservice.dto.response.UserReadingHistoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "reading-history-service")
public interface HistoryRecordClient {
    @GetMapping("/history/record/{userId}")
    ApiResponse<List<UserReadingHistoryResponse>> getUserReadingHistory(@PathVariable String userId);
}
