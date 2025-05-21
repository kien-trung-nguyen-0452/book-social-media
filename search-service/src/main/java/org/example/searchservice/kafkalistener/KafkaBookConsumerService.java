package org.example.searchservice.kafkalistener;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.readingservice.event.BookEvent;
import org.example.searchservice.mapper.BookIndexMapper;
import org.example.searchservice.repository.BookIndexRepository;
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

    @KafkaListener(topics = "book-deletion-topic", groupId = "elasticsearch")
    public void handleBookDeletion(BookEvent bookEvent) {
        log.info("Received event: {}", bookEvent);
        String bookId = bookEvent.getId();
        log.info("Received book deletion event for ID: {}", bookId);

        if (bookId == null || bookId.isEmpty()) {
            log.warn("Book ID is null or empty, skipping deletion");
            return;
        }

        try {
            bookIndexRepository.deleteById(bookId);
            log.info("Deleted book {} from Elasticsearch", bookId);
        } catch (Exception e) {
            log.error("Failed to delete book {} from Elasticsearch", bookId, e);
            // Có thể re-throw nếu muốn Kafka retry hoặc bỏ qua lỗi ở đây
        }
    }


}