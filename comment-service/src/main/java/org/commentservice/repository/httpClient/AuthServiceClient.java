package org.commentservice.repository.httpClient;

import org.commentservice.dto.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "auth-service")
@Repository
public interface AuthServiceClient {
    @GetMapping("/identity/users/get-user-id")
    ApiResponse<String> getUserId();
}
