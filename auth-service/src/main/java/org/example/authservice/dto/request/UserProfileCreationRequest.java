package org.example.authservice.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserProfileCreationRequest {
    String userId;
    String username;
    String email;
    String name;
}
