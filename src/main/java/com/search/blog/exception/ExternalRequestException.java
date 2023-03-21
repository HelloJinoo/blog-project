package com.search.blog.exception;

public class ExternalRequestException extends RuntimeException {
    public ExternalRequestException() {
    }

    public ExternalRequestException(String message) {
    }

    public ExternalRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExternalRequestException(Throwable cause) {
        super(cause);
    }

    public ExternalRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
