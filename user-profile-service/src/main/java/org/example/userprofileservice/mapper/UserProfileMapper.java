package org.example.userprofileservice.mapper;

import org.example.userprofileservice.dto.request.UserProfileCreationRequest;
import org.example.userprofileservice.dto.response.UserProfileCreationResponse;
import org.example.userprofileservice.dto.response.UserProfileResponse;
import org.example.userprofileservice.dto.response.UserReadingHistory;
import org.example.userprofileservice.dto.response.UserReadingHistoryResponse;
import org.example.userprofileservice.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface UserProfileMapper {
    UserProfileCreationResponse toUserProfileCreationResponse(UserProfile userProfile);

    @Mapping(source = "createdAt", target = "createdAt")
    UserProfile toUserProfile(UserProfileCreationRequest request);

    @Mapping(source = "createdAt", target = "createdAt")
    UserProfileResponse toUserProfileResponse(UserProfile userProfile);
}
