package com.rento.service.rentoservice.controllerAdvice;

import com.rento.service.rentoservice.dto.ErrorMessageDto;
import com.rento.service.rentoservice.exception.RoleNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RoleControllerAdvice {

    @ExceptionHandler({RoleNotFoundException.class})
    public ResponseEntity<ErrorMessageDto> handleException(RuntimeException exception) {
        ErrorMessageDto response = new ErrorMessageDto(exception.getMessage());

        return new ResponseEntity<>(response, new HttpHeaders(), ((RoleNotFoundException)exception).getHTTPStatus());
    }
}
