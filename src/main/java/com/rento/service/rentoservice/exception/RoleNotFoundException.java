package com.rento.service.rentoservice.exception;

import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends ResponseException {

    public static final String ROLE_NOT_FOUND_MESSAGE = "Role {name} not found!";

    public RoleNotFoundException(String name) {
        super(ROLE_NOT_FOUND_MESSAGE.replace("{name}", name));
    }

    @Override
    public HttpStatus getHTTPStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
