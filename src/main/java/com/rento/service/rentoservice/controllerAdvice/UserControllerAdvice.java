package com.rento.service.rentoservice.controllerAdvice;

import com.rento.service.rentoservice.dto.ErrorMessageDto;
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
    public ResponseEntity<ErrorMessageDto> handleException(RuntimeException exception) {
        ErrorMessageDto response = new ErrorMessageDto(exception.getMessage());

        return new ResponseEntity<>(response, new HttpHeaders(), ((UserNotFoundException)exception).getHTTPStatus());
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ErrorMessageDto> handleAuthenticationException(Exception ex) {
        ErrorMessageDto response = new ErrorMessageDto("Authentication failed");

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}