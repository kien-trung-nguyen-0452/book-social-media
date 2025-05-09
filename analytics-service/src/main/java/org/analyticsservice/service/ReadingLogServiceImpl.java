package org.analyticsservice.service;



import lombok.RequiredArgsConstructor;
import org.analyticsservice.dto.request.ReadingLogRequest;
import org.analyticsservice.dto.response.ReadingLogResponse;
import org.analyticsservice.entity.ReadingLog;
import org.analyticsservice.mapper.ReadingLogMapper;
import org.analyticsservice.repository.ReadingLogRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadingLogServiceImpl implements ReadingLogService {

    private final ReadingLogRepository repository;

    @Qualifier("readingLogMapperImpl")
    private final ReadingLogMapper mapper;

    @Override
    public ReadingLogResponse saveReadingLog(ReadingLogRequest request) {
        ReadingLog entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public List<ReadingLogResponse> getLogsByUser(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReadingLogResponse> getLogsByBook(Long bookId) {
        return repository.findByBookId(bookId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}

