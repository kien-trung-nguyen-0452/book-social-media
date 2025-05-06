package org.analyticsservice.repository;



import org.analyticsservice.entity.ReadingLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReadingLogRepository extends MongoRepository<ReadingLog, String> {
    List<ReadingLog> findByUserId(Long userId);
    List<ReadingLog> findByBookId(Long bookId);
}
