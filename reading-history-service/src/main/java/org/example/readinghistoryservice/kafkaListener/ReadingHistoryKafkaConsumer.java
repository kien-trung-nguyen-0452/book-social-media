package org.example.readinghistoryservice.kafkaListener;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.readinghistoryservice.exception.ErrorCode;
import org.example.readinghistoryservice.exception.ServiceException;
import org.example.readinghistoryservice.repository.HistoryRecordingRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReadingHistoryKafkaConsumer {

    private final HistoryRecordingRepository historyRecordingRepository;

    @KafkaListener(topics = "user-deleted-topic", groupId = "reading-history-service-group")
    public void listen(String message) {
        log.info("Received raw Kafka message: {}", message);

        try {
            if (historyRecordingRepository.existsByUserId(message)){
            historyRecordingRepository.deleteAllByUserId(message);
            log.info("Delete all history recording by user id: {}", message);}

            else {
                throw new ServiceException(ErrorCode.USER_NOT_EXISTED);
            }

        } catch (Exception e) {
            log.error("Failed to delete history records", e);
        }
    }
}