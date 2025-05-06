package org.analyticsservice.exception;



import org.analyticsservice.dto.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AnalysisServiceException.class)
    public ResponseEntity<ApiResponse<?>> handleAnalysisServiceException(AnalysisServiceException ex) {
        ErrorCode code = ex.getErrorCode();
        return ResponseEntity.status(code.getStatus())
                .body(new ApiResponse<>(null, ex.getMessage(), code.getStatus().value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleUnexpectedException(Exception ex) {
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(new ApiResponse<>(null, ErrorCode.INTERNAL_SERVER_ERROR.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value()));
    }
}
