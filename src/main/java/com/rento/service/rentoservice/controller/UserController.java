package com.rento.service.rentoservice.controller;

import com.rento.service.rentoservice.dto.SimpleResponseDto;
import com.rento.service.rentoservice.dto.user.UserRequestDto;
import com.rento.service.rentoservice.dto.user.UserResponseDto;
import com.rento.service.rentoservice.facade.UserFacade;
import com.rento.service.rentoservice.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Constants.API.USER.ROOT)
@CrossOrigin
public class UserController {

    private final UserFacade facade;

    @Autowired
    public UserController(UserFacade facade) {
        this.facade = facade;
    }

    @GetMapping(Constants.API.USER.ADMIN)
    public ResponseEntity<List<UserResponseDto>> getAllUsersExceptCaller(Authentication authentication) {
        return this.facade.getAllUsersExceptCaller(authentication);
    }

    @GetMapping
    public ResponseEntity<UserResponseDto> getUser(Authentication authentication) {
        return this.facade.getUser(authentication);
    }

    @PostMapping(Constants.API.USER.ADMIN)
    public ResponseEntity<SimpleResponseDto> createUserByAdmin(@RequestBody UserRequestDto request) {
        return this.facade.createUserByAdmin(request);
    }

    @PutMapping(Constants.API.USER.USER_UPDATE)
    public ResponseEntity<UserResponseDto> updateUser(Authentication authentication, @RequestBody UserRequestDto request) {
        return this.facade.updateUser(authentication, request);
    }

    @DeleteMapping(Constants.API.USER.USER_BY_USERNAME)
    public ResponseEntity<SimpleResponseDto> deleteUser(@PathVariable String username) {
        return this.facade.deleteUser(username);
    }
}