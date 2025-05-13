package org.example.crawlservice.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    int status;
    String message;
    T data;
}
