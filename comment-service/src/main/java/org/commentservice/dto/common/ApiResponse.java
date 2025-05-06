package org.commentservice.dto.common;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.codec.ServerSentEvent;

@Data
@AllArgsConstructor
@Builder
@JsonIncludeProperties(value = {"data", "message", "status"})
public class ApiResponse<T> {
    private T data;
    private String message;
    private int status;

}
