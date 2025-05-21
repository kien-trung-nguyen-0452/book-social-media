package org.chapterservice.exception;

import org.chapterservice.dto.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiResponse<Object>> handleServiceException(ServiceException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        ApiResponse<Object> response = ApiResponse.builder()
                .data(null)
                .message(errorCode.getMessage())
                .code(errorCode.getCode())
                .build();

        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .data(null)
                .message("Internal server error")
                .code(3003) // mã lỗi giống INTERNAL_ERROR trong ErrorCode
                .build();

        return ResponseEntity.status(500).body(response);
    }
}
