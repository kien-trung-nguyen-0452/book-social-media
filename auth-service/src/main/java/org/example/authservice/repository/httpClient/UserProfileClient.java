package org.example.authservice.repository.httpClient;

import jakarta.ws.rs.core.MediaType;

import org.example.authservice.config.FeintRetryerConfig;
import org.example.authservice.dto.common.ApiResponse;
import org.example.authservice.dto.request.UserProfileCreationRequest;
import org.example.authservice.dto.response.UserProfileCreationResponse;
import org.example.authservice.dto.response.UserProfileDeleteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-profile-service", configuration = {FeintRetryerConfig.class})
public interface UserProfileClient {
    @PostMapping(value = "/profile/internal/create", produces = MediaType.APPLICATION_JSON)
    ApiResponse<UserProfileCreationResponse> createUserProfile(@RequestBody UserProfileCreationRequest request);
    @DeleteMapping(value = "/profile/internal/delete/{userId}",produces = MediaType.APPLICATION_JSON)
    ApiResponse<UserProfileDeleteResponse> deleteUserProfile(@PathVariable("userId") String userId);
}
