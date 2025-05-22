package org.example.authservice.dto.event;

import lombok.Data;

import java.time.Instant;

@Data
public class UserDeletionEvent {
    private String eventType = "USER_DELETED";
    private String userId;
    private Instant timestamp;
}

