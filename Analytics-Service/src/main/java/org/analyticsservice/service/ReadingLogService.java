package org.analyticsservice.service;



import org.analyticsservice.dto.request.ReadingLogRequest;
import org.analyticsservice.dto.response.ReadingLogResponse;

import java.util.List;

public interface ReadingLogService {
    ReadingLogResponse saveReadingLog(ReadingLogRequest request);
    List<ReadingLogResponse> getLogsByUser(Long userId);
    List<ReadingLogResponse> getLogsByBook(Long bookId);
}
