package org.commentservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    COMMENT_NOT_FOUND(4001, "Comment not found", HttpStatus.NOT_FOUND),
    INVALID_COMMENT(4002, "Invalid comment data", HttpStatus.BAD_REQUEST),
    DUPLICATE_COMMENT(4003, "Duplicate comment", HttpStatus.CONFLICT),
    UNAUTHORIZED_ACCESS(4004, "Unauthorized access to comment", HttpStatus.FORBIDDEN),
    INTERNAL_ERROR(4999, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}

