package com.minimalistdev.reclameaqui.exception;

import org.springframework.http.HttpStatus;

public class LocaleNotFoundException extends RuntimeException{
    private final String message;

    public LocaleNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.NO_CONTENT;
    }
}
