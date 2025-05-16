package org.chapterservice.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class HistoryRecordRequest {
    String userId;
    String chapterId;
    String bookId;
    LocalDate lastReadAt;
}