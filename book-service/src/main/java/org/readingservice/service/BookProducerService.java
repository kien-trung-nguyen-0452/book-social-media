package org.readingservice.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.readingservice.event.BookEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookProducerService {

    KafkaTemplate<String, BookEvent> kafkaTemplate;

    @Async
    public void creationEven(BookEvent event){
        kafkaTemplate.send("book-creation-topic", event);
    }
}
