package com.rento.service.rentoservice.exception;

import org.springframework.http.HttpStatus;

public class TransportNotFoundException extends ResponseException {

    public static final String ROLE_NOT_FOUND_MESSAGE = "Transport {name} not found!";

    public TransportNotFoundException(String name) {
        super(ROLE_NOT_FOUND_MESSAGE.replace("{name}", name));
    }

    @Override
    public HttpStatus getHTTPStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
