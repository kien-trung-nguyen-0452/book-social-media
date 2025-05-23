package org.example.authservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authservice.dto.event.UserDeletionEvent;
import org.example.authservice.exception.ErrorCode;
import org.example.authservice.exception.ServiceException;
import org.example.authservice.repository.UserRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.TimeUnit;
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
}



