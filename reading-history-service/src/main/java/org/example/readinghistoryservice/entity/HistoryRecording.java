package org.example.readinghistoryservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("history-record")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryRecording {
    @Id
    String recordId;
    String userId;
    String chapterId;
    String bookId;
    LocalDate lastReadAt;
}
