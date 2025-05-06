package org.bookmarkservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    BOOKMARK_NOT_FOUND("BOOKMARK_NOT_FOUND", "Bookmark not found", HttpStatus.NOT_FOUND),
    DUPLICATE_BOOKMARK("DUPLICATE_BOOKMARK", "Bookmark already exists", HttpStatus.CONFLICT),
    INTERNAL_ERROR("INTERNAL_ERROR", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
