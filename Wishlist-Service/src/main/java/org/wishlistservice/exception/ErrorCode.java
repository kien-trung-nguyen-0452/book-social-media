package org.wishlistservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    WISHLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "Wishlist not found"),
    BOOK_ALREADY_EXISTS(HttpStatus.CONFLICT, "Book already in wishlist"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
