package org.example.crawlservice.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    int code;
    String message;
    T data;

}
