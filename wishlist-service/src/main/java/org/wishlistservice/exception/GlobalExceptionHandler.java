package org.wishlistservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.wishlistservice.dto.common.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException ex) {
        ErrorCode code = ex.getErrorCode();
        return ResponseEntity
                .status(code.getStatus())
                .body(new ApiResponse<>(null, code.getMessage(), code.getStatus().value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneral(Exception ex) {
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(new ApiResponse<>(null, ErrorCode.INTERNAL_SERVER_ERROR.getMessage(), 500));
    }
}
