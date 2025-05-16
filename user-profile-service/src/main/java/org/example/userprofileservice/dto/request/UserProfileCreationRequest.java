package org.example.userprofileservice.dto.request;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserProfileCreationRequest {
    String id;
    String userId;
    String name;
    String avatarUrl;
}
