package org.example.authservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserProfileCreationResponse {
    String id;
    String userId;
    String username;
    String email;
    String name;
}
