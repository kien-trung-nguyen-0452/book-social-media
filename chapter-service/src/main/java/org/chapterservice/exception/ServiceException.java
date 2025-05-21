package org.chapterservice.exception;

public class ServiceException extends RuntimeException {
    private final ErrorCode errorCode;

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // đảm bảo message được set
        this.errorCode = errorCode;
    }

    public ServiceException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public ServiceException(ErrorCode errorCode, String customMessage) {
        super(customMessage); // override message nếu cần
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
