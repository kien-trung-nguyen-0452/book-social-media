package org.example.authservice.dto.response;

import lombok.Data;

@Data
public class UserProfileResponse {
    String name;
    String avatarUrl;
    String id;
}
