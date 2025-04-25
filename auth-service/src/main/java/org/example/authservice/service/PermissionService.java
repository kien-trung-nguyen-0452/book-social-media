package org.example.authservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.example.authservice.dto.request.PermissionRequest;
import org.example.authservice.dto.response.PermissionResponse;
import org.example.authservice.entity.Permission;
import org.example.authservice.mapper.PermissionMapper;
import org.example.authservice.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class PermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionRequest permissionRequest) {
        Permission permission = permissionMapper.toPermission(permissionRequest);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAllPermissions() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).collect(Collectors.toList());
    }

    public void deleteById(String permissionId) {
        permissionRepository.deleteById(permissionId);
    }
}
