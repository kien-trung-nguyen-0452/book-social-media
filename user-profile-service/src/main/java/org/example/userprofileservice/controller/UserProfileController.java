package org.example.userprofileservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.userprofileservice.dto.ApiResponse;
import org.example.userprofileservice.dto.response.UserProfileResponse;
import org.example.userprofileservice.service.UserProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/info")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserProfileController {

    UserProfileService userProfileService;
    @GetMapping("/test")
    public String test(){
        return "test";
    }
    @GetMapping("/profile/{id}")
    public ApiResponse<UserProfileResponse> getUserProfileById( @PathVariable String id){
        return ApiResponse.<UserProfileResponse>builder()
                .code(1000)
                .result(userProfileService.getUserProfileByUserId(id))
                .build();

    }


}
