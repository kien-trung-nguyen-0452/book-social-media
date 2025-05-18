package org.readingservice.event;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor // <-- cần cho Jackson
@AllArgsConstructor // <-- cần cho @Builder để hoạt động đúng
public class UploadViewCountRequest {
    private String bookId;
    private int viewCount;
}
