package com.rento.service.rentoservice.controllerAdvice;

import com.rento.service.rentoservice.dto.auth.AuthResponseDto;
import com.rento.service.rentoservice.exception.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationControllerAdvice {

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<AuthResponseDto> handleException(RuntimeException exception) {
        AuthResponseDto response = new AuthResponseDto(exception.getMessage(), false);

        return new ResponseEntity<>(response, new HttpHeaders(), ((ValidationException) exception).getHTTPStatus());
    }
}
