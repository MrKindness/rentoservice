package com.rento.service.rentoservice.controllerAdvice;

import com.rento.service.rentoservice.dto.SimpleResponseDto;
import com.rento.service.rentoservice.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<SimpleResponseDto> handleUserNotFoundException(RuntimeException exception) {
        SimpleResponseDto response = new SimpleResponseDto(false, exception.getMessage());

        return new ResponseEntity<>(response, new HttpHeaders(), ((UserNotFoundException) exception).getHTTPStatus());
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<SimpleResponseDto> handleAuthenticationException(Exception ex) {
        SimpleResponseDto response = new SimpleResponseDto(false, "Authentication failed");

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}