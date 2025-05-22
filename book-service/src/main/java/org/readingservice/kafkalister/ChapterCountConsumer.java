package org.readingservice.kafkalister;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.readingservice.event.ChapterCountEvent;
import org.readingservice.repository.BookRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChapterCountConsumer {

    private final ObjectMapper objectMapper;
    private final BookRepository bookRepository;
    @KafkaListener(topics = "chapter-count-topic", groupId = "book-service-group")
    public void listen(String message) {
        log.info("Received raw Kafka message: {}", message);

        try {
            ChapterCountEvent event = objectMapper.readValue(message, ChapterCountEvent.class);

            var book = bookRepository.findBookById(event.getBookId());
            if (book == null) {
                log.warn("Book not found for id: {}", event.getBookId());
                return;
            }

            // Cộng dồn chapterCount, không để âm
            int newCount = Math.max(0, book.getChapterCount() + event.getCount());
            book.setChapterCount(newCount);
            bookRepository.save(book);

            log.info("Updated chapter count for book {} to {}", event.getBookId(), newCount);

        } catch (Exception e) {
            log.error("Failed to deserialize or update chapter count", e);
        }
    }

}
