package com.rento.service.rentoservice.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ResponseException {

    public static final String USER_NOT_FOUND_MESSAGE = "User {username} not found!";

    public UserNotFoundException(String username) {
        super(USER_NOT_FOUND_MESSAGE.replace("{username}", username));
    }

    @Override
    public HttpStatus getHTTPStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
