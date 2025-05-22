package org.example.searchservice.kafkalistener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.event.ChapterCountEvent;
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
    private final ObjectMapper objectMapper;


    @KafkaListener(topics = "book-creation-topic", groupId = "elasticsearch")
    public void listen(String message) {
        try {
            BookEvent bookEvent = objectMapper.readValue(message, BookEvent.class);
            log.info("Received BookEvent: {}", bookEvent);
            bookIndexRepository.save(mapper.toBookIndex(bookEvent));
        } catch (Exception e) {
            log.error("Failed to deserialize BookEvent", e);
        }
    }



    @KafkaListener(topics = "book-deletion-topic", groupId = "elasticsearch")
    public void handleBookDeletion(String message) {
        try {
            BookEvent bookEvent = objectMapper.readValue(message, BookEvent.class);
            log.info("Received event: {}", bookEvent);
            String bookId = bookEvent.getId();
            log.info("Received book deletion event for ID: {}", bookId);

            if (bookId == null || bookId.isEmpty()) {
                log.warn("Book ID is null or empty, skipping deletion");
                return;
            }

            bookIndexRepository.deleteById(bookId);
            log.info("Deleted book {} from Elasticsearch", bookId);

        } catch (Exception e) {
            log.error("Failed to deserialize or delete book from Elasticsearch", e);
        }
    }

    // Trong KafkaBookConsumerService của search-service
    @KafkaListener(topics = "chapter-count-topic", groupId = "elasticsearch")
    public void updateChapterCount(String message) {
        try {
            ChapterCountEvent event = objectMapper.readValue(message, ChapterCountEvent.class);

            var bookOpt = bookIndexRepository.findById(event.getBookId());
            if (bookOpt.isEmpty()) {
                log.warn("Book not found for id: {}", event.getBookId());
                return;
            }

            var bookIndex = bookOpt.get();
            // Cộng dồn, không âm
            int newCount = Math.max(0, bookIndex.getChapterCount() + event.getCount());
            bookIndex.setChapterCount(newCount);
            bookIndexRepository.save(bookIndex);

            log.info("Updated chapter count for book {} to {}", event.getBookId(), newCount);

        } catch (Exception e) {
            log.error("Failed to process ChapterCountEvent", e);
        }
    }

}






