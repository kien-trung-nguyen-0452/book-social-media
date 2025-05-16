package org.example.userprofileservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserReadingHistory {
    String chapterId;
    String bookId;
    LocalDate lastReadAt;
}
