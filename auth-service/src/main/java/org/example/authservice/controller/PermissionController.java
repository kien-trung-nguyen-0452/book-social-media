package org.example.authservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.authservice.dto.common.ApiResponse;
import org.example.authservice.dto.request.PermissionRequest;
import org.example.authservice.dto.response.PermissionResponse;
import org.example.authservice.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest permissionRequest) {
        var permission = permissionService.createPermission(permissionRequest);
        return ApiResponse.<PermissionResponse>builder().result(permission).build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> findAllPermissions() {
        var permissionList = permissionService.getAllPermissions();
        return ApiResponse.<List<PermissionResponse>>builder().result(permissionList).build();
    }

    @DeleteMapping("{permissionId}")
    ApiResponse<Void> deletePermission(@PathVariable String permissionId) {
        permissionService.deleteById(permissionId);
        return ApiResponse.<Void>builder().build();
    }
}
