package org.example.authservice.dto.response;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    String username;
    String userId;
    String email;
    String name;
    String avatarUrl;
    LocalDate createdAt;
}
