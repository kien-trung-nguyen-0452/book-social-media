package org.example.favouriteservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.favouriteservice.dto.event.BookEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final FavouriteService favouriteService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "user-deleted-topic", groupId = "favourite-service-group")
    public void handleUserDeletedEvent(ConsumerRecord<String, String> record) {
        try {
            String message = record.value();
            log.info("Received user deleted event: {}", message);

            JsonNode jsonNode = objectMapper.readTree(message);
            String username = jsonNode.get("username").asText();

            favouriteService.removeAllFavouriteBookByUsernameNoAuth(username);
            log.info("Deleted all favourite books for username: {}", username);
        } catch (Exception e) {
            log.error("Failed to handle user deleted event: {}", record.value(), e);
        }
    }
    @KafkaListener(topics = "book-deletion-topic", groupId = "favourite-service-group")
    public void handleBookDeletedEvent(String message) {
        try {
            log.info("Received book deletion event: {}", message);
            BookEvent event = objectMapper.readValue(message, BookEvent.class);
            favouriteService.removeAllFavouriteBookByBookIdWithoutAuth(event.getId());
            log.info("Removed all favourite books for bookId: {}", event.getId());
        } catch (Exception e) {
            log.error("Failed to handle book deletion event", e);
        }
    }

}
