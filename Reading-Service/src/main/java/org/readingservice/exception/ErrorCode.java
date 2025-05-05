package org.readingservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    BOOK_NOT_FOUND(2001, "Book not found", HttpStatus.NOT_FOUND),
    INVALID_BOOK_FORMAT(2002, "Invalid book format", HttpStatus.BAD_REQUEST),
    DUPLICATE_BOOK(2003, "Book already exists", HttpStatus.CONFLICT),
    UPLOAD_FAILED(2004, "Failed to upload book", HttpStatus.INTERNAL_SERVER_ERROR),
    UNSUPPORTED_FILE_TYPE(2005, "Unsupported file type", HttpStatus.BAD_REQUEST),
    CHAPTER_NOT_IN_BOOK(3002, "Chapter does not belong to the specified book", HttpStatus.BAD_REQUEST),
    CHAPTER_SERVICE_ERROR(3001, "Failed to retrieve chapter info", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

}

