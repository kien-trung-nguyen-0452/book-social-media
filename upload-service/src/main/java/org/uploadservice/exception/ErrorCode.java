package org.uploadservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    URLINVALID(4000,"URL INVALID",HttpStatus.BAD_REQUEST),
    FILE_UPLOAD_FAILED(4001, "File upload failed", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_NOT_FOUND(4002, "File not found", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN);;

    private final int code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
