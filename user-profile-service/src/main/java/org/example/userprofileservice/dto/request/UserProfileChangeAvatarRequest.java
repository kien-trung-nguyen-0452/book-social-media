package org.example.userprofileservice.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileChangeAvatarRequest {
    String id;
    String newAvatarUrl;
}
