package org.example.readinghistoryservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RecordResponse {
    String userId;
    String chapterId;
    String bookId;
    LocalDate lastReadAt;
}
