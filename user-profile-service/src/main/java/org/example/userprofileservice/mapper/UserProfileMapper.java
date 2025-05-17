package org.example.userprofileservice.mapper;

import org.example.userprofileservice.dto.request.UserProfileCreationRequest;
import org.example.userprofileservice.dto.response.UserProfileCreationResponse;
import org.example.userprofileservice.dto.response.UserProfileResponse;
import org.example.userprofileservice.dto.response.UserReadingHistory;
import org.example.userprofileservice.dto.response.UserReadingHistoryResponse;
import org.example.userprofileservice.entity.UserProfile;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfileCreationResponse toUserProfileCreationResponse(UserProfile userProfile);
    UserProfile toUserProfile (UserProfileCreationRequest request);
    UserProfileResponse toUserProfileResponse(UserProfile userProfile);

}
