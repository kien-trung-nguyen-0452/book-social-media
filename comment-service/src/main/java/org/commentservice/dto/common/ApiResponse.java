package org.commentservice.dto.common;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.http.codec.ServerSentEvent;

@Data
@AllArgsConstructor
@Builder
@JsonIncludeProperties()
public class ApiResponse<T> {
    private T result;
    private String message;
    private int code;
}
