package org.example.readinghistoryservice.mapper;


import org.example.readinghistoryservice.dto.request.RecordRequest;
import org.example.readinghistoryservice.dto.response.RecordResponse;
import org.example.readinghistoryservice.entity.HistoryRecording;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoryRecordMapper {
    HistoryRecording toHistoryRecord (RecordRequest request);
    RecordResponse toRecordResponse(HistoryRecording historyRecording);
}
