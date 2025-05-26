package org.example.userprofileservice.kafkalistener;



import org.example.userprofileservice.dto.event.UserCreatedEvent;
import org.example.userprofileservice.entity.UserProfile;
import org.example.userprofileservice.repository.UserProfileRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCreatedEventConsumer {

    private final ObjectMapper objectMapper;
    private final UserProfileRepository userProfileRepository;

    @KafkaListener(topics = "user-created-topic", groupId = "profile-service-group")
    public void consume(String message) {
        try {
            UserCreatedEvent event = objectMapper.readValue(message, UserCreatedEvent.class);
            log.info("Received UserCreatedEvent: {}", event);

            UserProfile profile = new UserProfile();
            profile.setUserId(event.getUserId());
            profile.setName(event.getName());
            userProfileRepository.save(profile);

            log.info("UserProfile created for userId: {}", event.getUserId());

        } catch (Exception e) {
            log.error("Failed to process UserCreatedEvent message: {}", message, e);
            // Bạn có thể thêm logic retry hoặc dead-letter queue ở đây nếu cần
        }
    }
}
