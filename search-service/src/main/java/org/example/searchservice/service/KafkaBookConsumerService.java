package org.example.searchservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.searchservice.event.BookEvent;
import org.example.searchservice.mapper.BookIndexMapper;
import org.example.searchservice.repository.BookIndexRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class KafkaBookConsumerService {
    BookIndexRepository bookIndexRepository;
    KafkaTemplate<String, BookEvent> kafkaTemplate;
    BookIndexMapper mapper;

    @KafkaListener(topics = "create-topic", groupId = "elasticsearch", containerFactory = "kafkaListenerContainerFactory")
    public void listen(BookEvent bookEvent) {
       bookIndexRepository.save(mapper.toBookIndex(bookEvent));
    }
}
