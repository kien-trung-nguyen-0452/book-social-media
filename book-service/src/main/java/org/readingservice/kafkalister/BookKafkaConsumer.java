package org.readingservice.kafkalister;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.readingservice.dto.request.UpdateViewCountRequest;
import org.readingservice.repository.BookRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
@Slf4j
public class BookKafkaConsumer {
    private final BookRepository bookRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "upload-view-count", groupId = "book-service-group")
    public void handleUpdateViewCount(UpdateViewCountRequest request) {
        log.info("Received view count update: {}", request);
        var book = bookRepository.findBookById(request.getBookId());
        int newViewCount = request.getViewCount() + book.getViewCount();
        book.setViewCount(newViewCount);
        bookRepository.save(book);
        log.info("Updated book: {}", book);
    }
}
