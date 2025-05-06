package org.uploadservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleServiceException(ServiceException ex) {
        return ResponseEntity.status(ex.getErrorCode().getStatus())
                .body(Map.of("error", ex.getErrorCode().getCode(), "message", ex.getMessage()));
    }
}
