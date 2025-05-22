package org.readingservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.readingservice.event.BookEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookProducerService {

    KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    @Async
    public void creationEven(BookEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            log.info("Sending book creation event: {}", message);
            kafkaTemplate.send("book-creation-topic", message);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize creation event", e);
        }
    }

    @Async
    public void deletionEvent(BookEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            log.info("Sending book deletion event: {}", message);
            kafkaTemplate.send("book-deletion-topic", message);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize deletion event", e);
        }
    }


}