package org.example.readinghistoryservice.kafkaListener;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.readinghistoryservice.dto.event.BookEvent;
import org.example.readinghistoryservice.exception.ErrorCode;
import org.example.readinghistoryservice.exception.ServiceException;
import org.example.readinghistoryservice.repository.HistoryRecordingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReadingHistoryKafkaConsumer {

    private ObjectMapper objectMapper;
    private final HistoryRecordingRepository historyRecordingRepository;
    @KafkaListener(topics = "user-deleted-topic", groupId = "reading-history-group")
    public void listen(String message) {
        log.info("Received raw Kafka message: {}", message);

        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            String userId = jsonNode.get("userId").asText();

            if (historyRecordingRepository.existsByUserId(userId)) {
                historyRecordingRepository.deleteAllByUserId(userId);
                log.info("Deleted all history recording by user id: {}", userId);
            } else
            {
                throw new ServiceException(ErrorCode.USER_NOT_EXISTED);
            }

        }catch (Exception e) {
            log.error("Failed to delete history records", e);
        }
    }

    @KafkaListener(topics = "book-deleted-topic", groupId = "reading-history-group")
    public void listenBookDeleted(String message) {
        log.info("Received raw Kafka message for deleted book: {}", message);

        try {

            BookEvent bookEvent = objectMapper.readValue(message, BookEvent.class);

            String bookId = bookEvent.getId();

            if (historyRecordingRepository.existsByBookId(bookId)) {
                historyRecordingRepository.deleteAllByBookId(bookId);
                log.info("Deleted all history recordings by book id: {}", bookId);
            } else {
                log.warn("No history records found for deleted book id: {}", bookId);
            }
        } catch (Exception e) {
            log.error("Failed to delete history records for book message: {}", message, e);
        }
    }


}