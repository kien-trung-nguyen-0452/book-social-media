package org.chapterservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    CHAPTER_NOT_FOUND(3001, "Chapter not found",HttpStatus.NOT_FOUND),
    INVALID_REQUEST(3002, "Invalid request", HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR(3003, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    DUPLICATE_CHAPTER(1008,"Dublicate chapter", HttpStatus.CONFLICT),
    BOOK_NOT_FOUND(1009, "Book not found", HttpStatus.NOT_FOUND),
    ;
    private final HttpStatus status;
    private final String message;
    private final int code;

    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
