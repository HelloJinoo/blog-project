package com.search.blog.exception;

public class ExternalServerException extends RuntimeException {

    public ExternalServerException() {
    }

    public ExternalServerException(String message) {
    }

    public ExternalServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExternalServerException(Throwable cause) {
        super(cause);
    }

    public ExternalServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
