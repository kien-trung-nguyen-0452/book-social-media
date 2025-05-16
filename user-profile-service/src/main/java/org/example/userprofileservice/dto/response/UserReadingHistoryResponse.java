package org.example.userprofileservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserReadingHistoryResponse {
    String recordId;
    String userId;
    String chapterId;
    String bookId;
    LocalDate lastReadAt;
}
