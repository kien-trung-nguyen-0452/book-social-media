package org.example.searchservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.readingservice.event.BookEvent;
import org.example.searchservice.mapper.BookIndexMapper;
import org.example.searchservice.repository.BookIndexRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class KafkaBookConsumerService {
    BookIndexRepository bookIndexRepository;
    BookIndexMapper mapper;


    @KafkaListener(topics = "book-creation-topic", groupId = "elasticsearch")
    public void listen(BookEvent bookEvent) {
        log.info("Received BookEvent in search-service: {}", bookEvent);
        bookIndexRepository.save(mapper.toBookIndex(bookEvent));
    }
}