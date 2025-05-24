package org.example.userprofileservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserProfileResponse {
    String id;
    String name;
    String avatarUrl;
    private LocalDateTime createdAt;
}
