package org.example.authservice.mapper;

import org.example.authservice.dto.request.RoleRequest;
import org.example.authservice.dto.response.RoleResponse;
import org.example.authservice.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest roleRequest);

    RoleResponse toRoleResponse(Role role);
}
