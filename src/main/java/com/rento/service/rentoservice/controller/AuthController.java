package com.rento.service.rentoservice.controller;

import com.rento.service.rentoservice.dto.auth.AuthRequestDto;
import com.rento.service.rentoservice.dto.auth.AuthResponseDto;
import com.rento.service.rentoservice.dto.user.UserRequestDto;
import com.rento.service.rentoservice.facade.AuthFacade;
import com.rento.service.rentoservice.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API.AUTH.ROOT)
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthFacade facade;

    @Autowired
    public AuthController(AuthFacade facade) {
        this.facade = facade;
    }

    @PostMapping(Constants.API.AUTH.REGISTER)
    public ResponseEntity<AuthResponseDto> register(@RequestBody UserRequestDto request) {
        return this.facade.register(request);
    }

    @PostMapping
    public ResponseEntity<AuthResponseDto> auth(@RequestBody AuthRequestDto request) {
        return this.facade.auth(request);
    }
}