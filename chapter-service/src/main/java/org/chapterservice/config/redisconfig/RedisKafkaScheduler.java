package org.chapterservice.config.redisconfig;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.chapterservice.dto.request.UploadViewCountRequest;
import org.chapterservice.service.ChapterKafkaProducerService;
import org.chapterservice.service.ViewCountService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RedisKafkaScheduler {

    final ViewCountService viewCountService;
    final ChapterKafkaProducerService chapterKafkaProducerService;

    @Scheduled(fixedRate = 60000) // 60s
    public void flushViewCountsToKafka() {
        Map<String, Integer> viewCounts = viewCountService.getViewCount();
        if (viewCounts == null || viewCounts.isEmpty()) {
            log.info("No views count");
            return;
        }

        viewCounts.forEach((bookId, viewCount) -> {
            log.info("Sending view count to kafka for bookId: {}, count: {}", bookId, viewCount);
            chapterKafkaProducerService.sendViewCount(
                    UploadViewCountRequest.builder()
                            .bookId(bookId)
                            .viewCount(viewCount)
                            .build()
            );
        });

    }
}
