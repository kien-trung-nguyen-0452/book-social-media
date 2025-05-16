package org.example.userprofileservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {
    String id;
    String name;
    String avatarUrl;
}
