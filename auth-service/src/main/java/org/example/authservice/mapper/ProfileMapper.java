package org.example.authservice.mapper;

import org.example.authservice.dto.request.UserCreateRequest;
import org.example.authservice.dto.request.UserProfileCreationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    UserProfileCreationRequest toProfileCreationRequest(UserCreateRequest request);
}
