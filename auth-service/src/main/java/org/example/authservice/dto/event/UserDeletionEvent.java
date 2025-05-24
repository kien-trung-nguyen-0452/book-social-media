package org.example.authservice.dto.event;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class UserDeletionEvent {
    String eventType = "USER_DELETED";
    String userId;
    String username;
    Instant timestamp;
}
