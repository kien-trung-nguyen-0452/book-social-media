package org.example.authservice.controller;

import java.util.List;

import org.example.authservice.dto.common.ApiResponse;
import org.example.authservice.dto.request.PermissionRequest;
import org.example.authservice.dto.response.PermissionResponse;
import org.example.authservice.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/permissions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(
        name = "Permission API",
        description = "API using for mange app's permission, using in authorization, tạm thời chưa dùng")
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    @Operation(
            summary = "create permission",
            security = {@SecurityRequirement(name = "BearToken"), @SecurityRequirement(name = "ADMIN role")})
    ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest permissionRequest) {
        var permission = permissionService.createPermission(permissionRequest);
        return ApiResponse.<PermissionResponse>builder().result(permission).build();
    }

    @GetMapping
    @PostMapping
    @Operation(
            summary = "get all permission",
            security = {@SecurityRequirement(name = "BearToken"), @SecurityRequirement(name = "ADMIN role")})
    ApiResponse<List<PermissionResponse>> findAllPermissions() {
        var permissionList = permissionService.getAllPermissions();
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionList)
                .build();
    }

    @DeleteMapping("{permissionId}")
    @PostMapping
    @Operation(
            summary = "delete permission by permission id",
            security = {@SecurityRequirement(name = "BearToken"), @SecurityRequirement(name = "ADMIN role")})
    ApiResponse<Void> deletePermission(@PathVariable String permissionId) {
        permissionService.deleteById(permissionId);
        return ApiResponse.<Void>builder().build();
    }
}
