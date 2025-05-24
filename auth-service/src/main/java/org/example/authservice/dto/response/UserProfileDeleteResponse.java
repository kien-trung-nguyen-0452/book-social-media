package org.example.authservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileDeleteResponse {
    String id;
    String userId;
    String username;
    String email;
    String name;
}
