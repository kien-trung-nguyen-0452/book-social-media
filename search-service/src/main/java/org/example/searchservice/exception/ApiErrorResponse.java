package org.example.searchservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiErrorResponse {
    private int code;
    private String message;
    private LocalDateTime timestamp;
}
