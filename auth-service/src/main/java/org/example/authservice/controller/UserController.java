package org.example.authservice.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.example.authservice.dto.common.ApiResponse;
import org.example.authservice.dto.request.UserCreateRequest;
import org.example.authservice.dto.request.UserUpdateRequest;
import org.example.authservice.dto.response.UserResponse;
import org.example.authservice.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        var userResponse = userService.createUser(userCreateRequest);
        return ApiResponse.<UserResponse>builder().result(userResponse).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<List<UserResponse>> findAllUsers() {
        var userList = userService.findAllUser();
        return ApiResponse.<List<UserResponse>>builder().result(userList).build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> findUserById(@PathVariable String userId) {
        var userResponse = userService.findUserById(userId);
        return ApiResponse.<UserResponse>builder().result(userResponse).build();
    }

    @GetMapping("/info")
    ApiResponse<UserResponse> getInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(
            @PathVariable String userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        var userResponse = userService.updateUser(userUpdateRequest, userId);
        return ApiResponse.<UserResponse>builder().result(userResponse).build();
    }
}
