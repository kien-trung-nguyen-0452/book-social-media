package org.example.readinghistoryservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.readinghistoryservice.dto.request.RecordRequest;
import org.example.readinghistoryservice.dto.response.RecordResponse;

import org.example.readinghistoryservice.exception.ErrorCode;
import org.example.readinghistoryservice.exception.ServiceException;
import org.example.readinghistoryservice.mapper.HistoryRecordMapper;
import org.example.readinghistoryservice.repository.HistoryRecordingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class HistoryRecordingService {
    HistoryRecordingRepository historyRecordingRepository;
    HistoryRecordMapper mapper;

    public RecordResponse recordHistory (RecordRequest request){
        if(historyRecordingRepository.existsByUserIdAndBookId(request.getUserId(), request.getBookId())){
            var record = historyRecordingRepository.findByUserIdAndBookId(request.getUserId(), request.getBookId());
            record.setChapterId(request.getChapterId());
            record.setLastReadAt(request.getLastReadAt());
            return mapper.toRecordResponse(historyRecordingRepository.save(record));
        }
        var record = mapper.toHistoryRecord(request);
        return mapper.toRecordResponse(historyRecordingRepository.save(record));
    }

    public Page<RecordResponse> getRecordsByUserId(String userId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        if(!historyRecordingRepository.existsByUserId(userId)){
            throw new ServiceException(ErrorCode.USER_NOT_EXISTED);
        }

        var historyPage = historyRecordingRepository.findAllByUserIdOrderByLastReadAtDesc(userId, pageable);

        return historyPage.map(mapper::toRecordResponse); // convert từng phần tử
    }

}
