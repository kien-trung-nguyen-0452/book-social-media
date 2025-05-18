package org.chapterservice.config.redisconfig;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.chapterservice.dto.request.UploadViewCountRequest;
import org.chapterservice.service.ChapterKafkaProducerService;
import org.chapterservice.service.ViewCountService;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RedisKafkaScheduler {

    ViewCountService viewCountService;
    ChapterKafkaProducerService chapterKafkaProducerService;

    @Scheduled(fixedRate = 5000) // 60 giây
    public void flushViewCountsToKafka() {
        Map<String, Integer> views = viewCountService.getViewCount();
        if (views.isEmpty()) {
            log.info("No view counts to send.");
            return;
        }

        views.forEach((bookId, viewCount) -> {
            log.info("Sending view count to kafka for bookId: {}", bookId);
            chapterKafkaProducerService.sendViewCount(
                    UploadViewCountRequest.builder()
                            .bookId(bookId)
                            .viewCount(viewCount)
                            .build()
            );
        });
    }
}
