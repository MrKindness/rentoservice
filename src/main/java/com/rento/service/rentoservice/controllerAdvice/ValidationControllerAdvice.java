package com.rento.service.rentoservice.controllerAdvice;

import com.rento.service.rentoservice.dto.SimpleResponseDto;
import com.rento.service.rentoservice.exception.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationControllerAdvice {

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<SimpleResponseDto> handleException(RuntimeException exception) {
        SimpleResponseDto response = new SimpleResponseDto(false, exception.getMessage());

        return new ResponseEntity<>(response, new HttpHeaders(), ((ValidationException) exception).getHTTPStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<SimpleResponseDto> handleUnknownException(RuntimeException exception) {
        SimpleResponseDto response = new SimpleResponseDto(false, "Unknown Error!");

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
