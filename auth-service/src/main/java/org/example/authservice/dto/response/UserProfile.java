package org.example.authservice.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
