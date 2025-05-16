package org.example.readinghistoryservice.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RecordRequest {
    String userId;
    String chapterId;
    String bookId;
    LocalDate lastReadAt;
}
