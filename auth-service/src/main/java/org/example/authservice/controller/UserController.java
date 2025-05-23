package org.example.authservice.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.example.authservice.dto.common.ApiResponse;
import org.example.authservice.dto.request.ChangePasswordRequest;
import org.example.authservice.dto.request.UserCreateRequest;
import org.example.authservice.dto.request.UserUpdateRequest;
import org.example.authservice.dto.response.ChangePasswordResponse;
import org.example.authservice.dto.response.UserProfile;
import org.example.authservice.dto.response.UserResponse;
import org.example.authservice.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "User API", description = "API using for create user, get user info, registration, and update user")
public class UserController {
    private UserService userService;

    @PostMapping("/registration")
    @Operation(
            method = "POST",
            summary = "registration",
            description = "create user account then pushing it to user-profile-service to create user profile",
            security = @SecurityRequirement(name = "none"))
    @ApiResponses(
            value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "register success"),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "1002",
                        description = "user existed"),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "1003",
                        description = "invalid username"),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "1004",
                        description = "invalid password"),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "1009",
                        description = "email existed")
            })
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        var userResponse = userService.createUser(userCreateRequest);
        return ApiResponse.<UserResponse>builder().result(userResponse).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            method = "GET",
            summary = "find all users",
            description = "find all user existed",
            security =
                    @SecurityRequirement(
                            name = "BearToken",
                            scopes = {"ADMIN"}))
    @ApiResponses(
            value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "success"),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "1006",
                        description = "unauthenticated"),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "1007",
                        description = "not the admin")
            })
    ApiResponse<List<UserResponse>> findAllUsers() {
        var userList = userService.findAllUser();
        return ApiResponse.<List<UserResponse>>builder().result(userList).build();
    }

    @Operation(
            method = "GET",
            summary = "find all users",
            description = "get user by id",
            security =
                    @SecurityRequirement(
                            name = "BearToken",
                            scopes = {"User"}))
    @ApiResponses(
            value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "success"),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "1005",
                        description = "user not existed"),
            })
    @GetMapping("/{userId}")
    ApiResponse<UserResponse> findUserById(@PathVariable String userId) {
        var userResponse = userService.findUserById(userId);
        return ApiResponse.<UserResponse>builder().result(userResponse).build();
    }

    @GetMapping("/info")
    @Operation(
            method = "GET",
            summary = "find all users",
            description = "get the user's profile",
            security =
                    @SecurityRequirement(
                            name = "BearToken",
                            scopes = {"this user"}))
    @ApiResponses(
            value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "success"),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "1005",
                        description = "user not existed"),
            })
    ApiResponse<UserProfile> getInfo() {
        return ApiResponse.<UserProfile>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @Operation(
            method = "PUT",
            security =
                    @SecurityRequirement(
                            name = "BearToken",
                            scopes = {"this user"}))
    @ApiResponses(
            value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "success"),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "1005",
                        description = "user not existed"),
            })
    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(
            @PathVariable String userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        var userResponse = userService.updateUser(userUpdateRequest, userId);
        return ApiResponse.<UserResponse>builder().result(userResponse).build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUserProfile(userId);
        return ApiResponse.<Void>builder()
                .message("User and profile deleted successfully")
                .build();
    }


    @Operation(
            method = "PUT",
            summary = "change password",
            security =
            @SecurityRequirement(
                    name = "BearToken",
                    scopes = {"this user"}))
    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "success"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "1005",
                            description = "user not existed"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "1010",
                            description = "Invalid current password"),
            })
    @PutMapping("/change-password/{userId}")
    ApiResponse<ChangePasswordResponse> changePassword(@PathVariable String userId, @RequestBody ChangePasswordRequest request){
        return ApiResponse.<ChangePasswordResponse>builder()
                .result(userService.changePassword(userId, request))
                .build();
    }

    @Operation(
            method = "GET",
            summary = "get current user id",
            security =
            @SecurityRequirement(
                    name = "BearToken",
                    scopes = {"this user"}))
    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "", description = "success"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "1005",
                            description = "user not existed"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "1006",
                            description = "unauthenticated")
            })
    @GetMapping("/get-user-id")
    ApiResponse<String> getUserId(){
        return ApiResponse.<String>builder()
                .result(userService.getUserId())
                .build();
    }
}
