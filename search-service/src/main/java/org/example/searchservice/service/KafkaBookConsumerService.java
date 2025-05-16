package org.example.searchservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.readingservice.event.BookEvent;
import org.example.searchservice.mapper.BookIndexMapper;
import org.example.searchservice.repository.BookIndexRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class KafkaBookConsumerService {
    BookIndexRepository bookIndexRepository;
    BookIndexMapper mapper;

    @KafkaListener(topics = "book-creation-topic", groupId = "elasticsearch")
    public void listen(BookEvent bookEvent) {
        bookIndexRepository.save(mapper.toBookIndex(bookEvent));
    }
}