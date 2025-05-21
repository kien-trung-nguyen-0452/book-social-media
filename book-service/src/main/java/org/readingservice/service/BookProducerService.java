package org.readingservice.service;


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

    KafkaTemplate<String, BookEvent> kafkaTemplate;

    @Async
    public void creationEven(BookEvent event) {
        log.info("Sending book creation event: {}", event);
        kafkaTemplate.send("book-creation-topic", event);
    }
    @Async
    public void deletionEvent(BookEvent event) {
        log.info("Sending book deletion event: {}", event);
        kafkaTemplate.send("book-deletion-topic", event);
    }

}