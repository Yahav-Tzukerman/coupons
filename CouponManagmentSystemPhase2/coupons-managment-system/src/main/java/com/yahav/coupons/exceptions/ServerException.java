package com.yahav.coupons.exceptions;

import com.yahav.coupons.enums.ErrorType;

public class ServerException extends Exception {
    private final ErrorType errorType;

    public ServerException(ErrorType errorType) {
        super(errorType.getErrorMessage() + ": " + errorType.getErrorNumber());
        this.errorType = errorType;
        if (errorType.isShowStackTrace()) {
            printStackTrace();
        }
    }

    public ServerException(ErrorType errorType, String message) {
        super(message + ": " + errorType.getErrorMessage() + ": " + errorType.getErrorNumber());
        this.errorType = errorType;
        if (errorType.isShowStackTrace()) {
            printStackTrace();
        }
    }

    public ServerException(Exception e, ErrorType errorType) {
        super(e.getMessage() + ": " + errorType.getErrorMessage() + ": " + errorType.getErrorNumber(), e);
        this.errorType = errorType;
        if (errorType.isShowStackTrace()) {
            printStackTrace();
        }
    }

    public ServerException(Exception e, ErrorType errorType, String message) {
        super(e.getMessage() + ": " + message + ": " + errorType.getErrorMessage() + ": " + errorType.getErrorNumber(), e);
        this.errorType = errorType;
        if (errorType.isShowStackTrace()) {
            printStackTrace();
        }
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
