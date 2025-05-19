package org.example.userprofileservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.userprofileservice.dto.ApiResponse;
import org.example.userprofileservice.dto.request.UserProfileChangeAvatarRequest;
import org.example.userprofileservice.dto.request.UserProfileCreationRequest;
import org.example.userprofileservice.dto.request.UserProfileUpdateRequest;
import org.example.userprofileservice.dto.response.*;
import org.example.userprofileservice.service.UserProfileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/internal")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class InternalUserProfileController {
    UserProfileService userProfileService;
    private final RestClient.Builder builder;

    @PostMapping("/create")
    public ApiResponse<UserProfileCreationResponse> createUserProfile(@RequestBody UserProfileCreationRequest request){
        return ApiResponse.<UserProfileCreationResponse>builder()
                .code(1000)
                .result(userProfileService.createUserProfile(request))
                .build();
    }

    @PutMapping("/change-avatar")
    public ApiResponse<UserProfileChangeAvatarResponse> changeAvatar(@RequestBody UserProfileChangeAvatarRequest request){
        return ApiResponse.<UserProfileChangeAvatarResponse>builder()
                .code(1000)
                .result(userProfileService.changeAvatar(request))
                .build();
    }

    @PutMapping("/update-user-profile")
    public ApiResponse<UserProfileUpdateResponse> updateUserProfile (@RequestBody UserProfileUpdateRequest request){
        return ApiResponse.<UserProfileUpdateResponse>builder()
                .code(1000)
                .result(userProfileService.updateUserProfile(request))
                .build();
    }

    @GetMapping("/get-reading-history/{userId}")
    public ApiResponse<List<UserReadingHistory>> getUserReadingHistory(@PathVariable String userId){
        return ApiResponse.<List<UserReadingHistory>>builder()
                .code(1000)
                .result(userProfileService.getUserReadingHistory(userId))
                .message("user id :" + userId)
                .build();
    }
    @DeleteMapping("delete/{userId}")
    public ApiResponse<UserProfileDeleteResponse> deleteUserProfile(@PathVariable String userId) {
        return ApiResponse.<UserProfileDeleteResponse>builder()
                .code(1000)
                .result(userProfileService.deleteUserProfile(userId))
                .message("Deleted profile for userId: " + userId)
                .build();
    }

}
