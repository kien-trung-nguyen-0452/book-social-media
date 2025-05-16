package org.example.userprofileservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileChangeAvatarResponse {
    String id;
    String newAvatarUrl;
}
