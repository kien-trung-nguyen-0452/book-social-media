package org.chapterservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class HistoryRecordResponse {
    String userId;
    String chapterId;
    String bookId;
    LocalDate lastReadAt;
    boolean isSuccess;
}