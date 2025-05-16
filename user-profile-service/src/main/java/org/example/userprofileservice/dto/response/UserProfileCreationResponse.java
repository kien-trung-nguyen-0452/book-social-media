package org.example.userprofileservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileCreationResponse {
    String id;
    String userId;
    String name;
    String avatarUrl;
}
