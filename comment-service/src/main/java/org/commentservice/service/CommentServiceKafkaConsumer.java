package org.commentservice.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceKafkaConsumer {

    private final CommentService commentService; // Service chứa logic xóa comment theo userId

    @KafkaListener(topics = "user-deleted-topic", groupId = "comment-service-group")
    public void handleUserDeletedEvent(ConsumerRecord<String, String> record) {
        try {
            String message = record.value();
            log.info("Received user deleted event: {}", message);

            // Giả sử message chỉ là userId dưới dạng chuỗi (String)
            String userId = message;

            commentService.deleteWithoutPermission(userId);  // gọi đến service xử lý logic xóa
            log.info("Deleted all comments for userId: {}", userId);
        } catch (Exception e) {
            log.error("Failed to handle user deleted event", e);
        }
    }
}