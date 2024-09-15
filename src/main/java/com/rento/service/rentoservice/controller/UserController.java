package com.rento.service.rentoservice.controller;

import com.rento.service.rentoservice.dto.SimpleResponseDto;
import com.rento.service.rentoservice.dto.user.UserRequestDto;
import com.rento.service.rentoservice.dto.user.UserResponseDto;
import com.rento.service.rentoservice.dto.user.UserRoleRequestDto;
import com.rento.service.rentoservice.facade.UserFacade;
import com.rento.service.rentoservice.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Constants.API.USER.ROOT)
@CrossOrigin
public class UserController {

    private final UserFacade facade;

    @Autowired
    public UserController(UserFacade facade) {
        this.facade = facade;
    }

    @GetMapping(Constants.API.USER.USERS_ALL_ADMIN)
    public ResponseEntity<List<UserResponseDto>> getAllUsersExceptCaller(Authentication authentication) {
        return this.facade.getAllUsersExceptCaller(authentication);
    }

    @GetMapping
    public ResponseEntity<UserResponseDto> getUser(Authentication authentication) {
        return this.facade.getUser(authentication);
    }

    @PutMapping(Constants.API.USER.USER_UPDATE)
    public ResponseEntity<SimpleResponseDto> updateUser(Authentication authentication, @RequestBody UserRequestDto request) {
        return this.facade.updateUser(authentication, request);
    }

    @PutMapping(Constants.API.USER.USER_ROLE)
    public ResponseEntity<SimpleResponseDto> updateRole(@RequestBody UserRoleRequestDto request) {
        return this.facade.updateRole(request);
    }

    @DeleteMapping(Constants.API.USER.USER_BY_ID)
    public ResponseEntity<SimpleResponseDto> deleteUser(@PathVariable UUID userId) {
        return this.facade.deleteUser(userId);
    }
}