package org.commentservice.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceKafkaConsumer {

    ObjectMapper mapper = new ObjectMapper();
    private final CommentService commentService;

    @KafkaListener(topics = "user-deleted-topic", groupId = "comment-service-group")
    public void handleUserDeletedEvent(ConsumerRecord<String, String> record) {
        try {
            String message = record.value();
            log.info("Received user deleted event: {}", message);

            // Parse JSON
            JsonNode jsonNode = mapper.readTree(message);
            String username = jsonNode.get("username").asText();

            commentService.deleteByUsernameWithoutAuth(username);
            log.info("Deleted all comments for username: {}", username);
        } catch (Exception e) {
            log.error("Failed to handle user deleted event for message: {}", record.value(), e);
        }
    }

}