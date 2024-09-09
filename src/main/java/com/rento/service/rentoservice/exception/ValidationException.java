package com.rento.service.rentoservice.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends ResponseException {

    public ValidationException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHTTPStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
