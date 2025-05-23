package org.example.readinghistoryservice.repository;

import org.example.readinghistoryservice.entity.HistoryRecording;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface HistoryRecordingRepository extends MongoRepository<HistoryRecording, String> {

    boolean existsByUserId(String userId);
    boolean existsByUserIdAndBookId(String userId, String bookId);

   HistoryRecording findByUserIdAndBookId(String userId, String bookId);

    Page<HistoryRecording> findAllByUserIdOrderByLastReadAtDesc(String userId, Pageable pageable);

    void deleteHistoryRecordingByUserId(String userId);
}
