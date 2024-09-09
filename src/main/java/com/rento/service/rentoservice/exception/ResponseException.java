package com.rento.service.rentoservice.exception;

import org.springframework.http.HttpStatus;

public abstract class ResponseException extends RuntimeException {

    public ResponseException(String message) {
        super(message);
    }

    public abstract HttpStatus getHTTPStatus();
}
