package org.chapterservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.chapterservice.event.ChapterCountEvent;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.Message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chapterservice.dto.request.UploadViewCountRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChapterKafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    @Value("${spring.kafka.topic.chapter-count}")
    private String chapterCountTopic;
    public void sendViewCount(UploadViewCountRequest request) {
        try {
            String json = objectMapper.writeValueAsString(request);
            log.info("Sending JSON to Kafka: {}", json);
            kafkaTemplate.send("upload-view-count", request.getBookId(), json);
        } catch (JsonProcessingException e) {
            log.error("Error serializing UploadViewCountRequest", e);
        }
    }

    public void sendChapterCountEvent(ChapterCountEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(chapterCountTopic, json);
            log.info("Chapter count event sent: {}", json);
        } catch (JsonProcessingException e) {
            log.error("Error serializing ChapterCountEvent", e);
        }
    }
}

