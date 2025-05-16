package org.example.readinghistoryservice.exception;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ServiceException extends RuntimeException {

    private ErrorCode errorCode;

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
