package org.analyticsservice.exception;


import lombok.Getter;

@Getter
public class AnalysisServiceException extends RuntimeException {
    private final ErrorCode errorCode;

    public AnalysisServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AnalysisServiceException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
    }
}

