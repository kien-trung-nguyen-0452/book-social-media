package org.bookmarkservice.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private T result;
    private String message;
    private int status;
}
