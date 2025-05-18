package org.chapterservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ViewCountService {

    RedisTemplate<String, String> redisTemplate;

    public void incrementViewCount(String bookId) {
        String key = "viewCount:book:" + bookId;
        redisTemplate.opsForValue().increment(key, 1);
    }

    public Map<String, Integer> getViewCount() {
        Set<String> keys = redisTemplate.keys("viewCount:book:*");
        if (keys == null || keys.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, Integer> result = new HashMap<>();
        for (String key : keys) {
            String value = redisTemplate.opsForValue().get(key);
            if (value == null) continue;

            String bookId = key.replace("viewCount:book:", "");
            try {
                result.put(bookId, Integer.parseInt(value));
                redisTemplate.delete(key); // xóa sau khi gửi
            } catch (NumberFormatException ignored) {}
        }
        return result;
    }

}
