package org.chapterservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.chapterservice.dto.request.UploadViewCountRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
@Slf4j
public class ChapterKafkaProducerService {

    private final KafkaTemplate<String, String> stringKafkaTemplate;  // tên trùng với bean

    private final ObjectMapper objectMapper;

    public void sendViewCount(UploadViewCountRequest request) {
        try {
            String json = objectMapper.writeValueAsString(request);
            log.info("Sending JSON view count to Kafka: {}", json);
            stringKafkaTemplate.send("upload-view-count", json);
        } catch (JsonProcessingException e) {
            log.error("Error serializing UploadViewCountRequest", e);
        }
    }
}

