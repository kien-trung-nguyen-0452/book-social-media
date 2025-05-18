package org.chapterservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ViewCountService {

    RedisTemplate<String, Integer> redisTemplate;
    private static final String PREFIX = "bookId:";

    public void incrementViewCount(String bookId){
        String key = PREFIX + bookId;
        log.info("+ 1 views count to key: {}", key);
        var count = redisTemplate.opsForValue().increment(key, 1);
        log.info("current count {}", count);
    }

    public Map<String, Integer> getViewCount(){
        Set<String> keys = scanKeys(PREFIX + "*");
        log.info("Found {} keys with prefix {}", keys.size(), PREFIX);
        Map<String, Integer> result = new HashMap<>();

        if (!keys.isEmpty()) {
            for (String key : keys) {
                log.info("view count of {}", key);
                Integer count = redisTemplate.opsForValue().get(key);
                if (count != null) {
                    String bookId = key.replace(PREFIX, "");
                    result.put(bookId, count);
                    redisTemplate.delete(key);
                }
            }
        }
        return result;
    }

    public Set<String> scanKeys(String pattern) {
        Set<String> keys = new HashSet<>();
        ScanOptions options = ScanOptions.scanOptions().match(pattern).count(1000).build();
        Cursor<byte[]> cursor = redisTemplate.getConnectionFactory().getConnection().scan(options);

        while (cursor.hasNext()) {
            keys.add(new String(cursor.next(), StandardCharsets.UTF_8));
        }
        cursor.close();
        return keys;
    }
}
