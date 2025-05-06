package org.readingservice.client;



import org.readingservice.client.dto.analytics.AnalyticsRequest;
import org.readingservice.client.dto.analytics.AnalyticsResponse;
import org.readingservice.dto.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "analytics-service", url = "${service.analytics.base-url}")
public interface AnalyticsServiceClient {


    @PostMapping("/api/analytics/track")
    ApiResponse<Void> trackReading(@RequestBody AnalyticsRequest request);


    @GetMapping("/api/analytics/user/{userId}/books")
    ApiResponse<List<AnalyticsResponse>> getUserReadingStats(@PathVariable("userId") Long userId);
}
