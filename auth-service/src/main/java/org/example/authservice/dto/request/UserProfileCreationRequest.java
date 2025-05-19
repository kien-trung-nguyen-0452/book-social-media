package org.example.authservice.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileCreationRequest {
    String userId;
    String username;
    String email;
    String name;
}
