package org.example.authservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.event.UserDeletionEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendUserDeletedEvent(String userId) {
        try {
            UserDeletionEvent event = new UserDeletionEvent();
            event.setUserId(userId);
            event.setTimestamp(Instant.now());

            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("user-deleted-topic", message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send user deleted event", e);
        }
    }
}
