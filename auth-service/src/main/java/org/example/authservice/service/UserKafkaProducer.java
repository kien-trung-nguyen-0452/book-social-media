package org.example.authservice.service;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import org.example.authservice.dto.event.UserCreatedEvent;
import org.example.authservice.dto.event.UserDeletionEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendUserDeletedEvent(String userId, String username) {
        try {
            UserDeletionEvent event = new UserDeletionEvent();
            event.setUserId(userId);
            event.setUsername(username);
            event.setTimestamp(Instant.now());
            String message = objectMapper.writeValueAsString(event);

            kafkaTemplate.send("user-deleted-topic", message).get(5, TimeUnit.SECONDS);

            log.info("User deleted event sent for user: {}", username);

        } catch (Exception e) {
            log.error("Failed to send user deleted event for user: {}", username, e);
            throw new RuntimeException("Failed to send user deleted event", e);
        }
    }
    public void sendUserCreatedEvent(String userId, String username, String name, String email) {
        try {
            UserCreatedEvent event = UserCreatedEvent.builder()
                    .userId(userId)
                    .username(username)
                    .name(name)
                    .email(email)
                    .build();

            String message = objectMapper.writeValueAsString(event);

            kafkaTemplate.send("user-created-topic", message).get(5, TimeUnit.SECONDS);

            log.info("User created event sent for user: {}", username);

        } catch (Exception e) {
            log.error("Failed to send user created event for user: {}", username, e);
            throw new RuntimeException("Failed to send user created event", e);
        }
    }

}
