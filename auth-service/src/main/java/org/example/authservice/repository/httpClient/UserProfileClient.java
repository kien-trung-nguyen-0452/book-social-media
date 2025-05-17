package org.example.authservice.repository.httpClient;

import jakarta.ws.rs.core.MediaType;
import org.example.authservice.dto.common.ApiResponse;
import org.example.authservice.dto.request.UserProfileCreationRequest;
import org.example.authservice.dto.response.UserProfileCreationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-profile-service")
public interface UserProfileClient {
    @PostMapping( value = "/profile/internal/create",produces = MediaType.APPLICATION_JSON)
    ApiResponse<UserProfileCreationResponse> createUserProfile(@RequestBody UserProfileCreationRequest request);
}
