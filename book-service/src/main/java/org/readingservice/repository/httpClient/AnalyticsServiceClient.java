package org.readingservice.repository.httpClient;



import org.readingservice.repository.httpClient.dto.analytics.AnalyticsRequest;
import org.readingservice.repository.httpClient.dto.analytics.AnalyticsResponse;
import org.readingservice.dto.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "analytics-service")
public interface AnalyticsServiceClient {


    @PostMapping("/analytics/track")
    ApiResponse<Void> trackReading(@RequestBody AnalyticsRequest request);


    @GetMapping("/analytics/user/{userId}/books")
    ApiResponse<List<AnalyticsResponse>> getUserReadingStats(@PathVariable("userId") Long userId);
}
