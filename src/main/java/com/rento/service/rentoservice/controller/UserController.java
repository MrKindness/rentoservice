package com.rento.service.rentoservice.controller;

import com.rento.service.rentoservice.dto.user.UserResponseDto;
import com.rento.service.rentoservice.facade.UserFacade;
import com.rento.service.rentoservice.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API.USER.ROOT)
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserFacade facade;

    @Autowired
    public UserController(UserFacade facade) {
        this.facade = facade;
    }

    @GetMapping
    public ResponseEntity<UserResponseDto> getUser(Authentication authentication) {
        return this.facade.getUser(authentication);
    }

    @GetMapping(Constants.API.USER.ADMIN)
    public ResponseEntity<UserResponseDto> getUserAdmin(Authentication authentication) {
        return this.facade.getUser(authentication);
    }
}
