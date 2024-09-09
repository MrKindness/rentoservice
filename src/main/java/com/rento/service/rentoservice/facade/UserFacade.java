package com.rento.service.rentoservice.facade;

import com.rento.service.rentoservice.dto.user.UserResponseDto;
import com.rento.service.rentoservice.entity.user.User;
import com.rento.service.rentoservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {

    private final UserService userService;

    @Autowired
    public UserFacade(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<UserResponseDto> getUser(Authentication authentication) {
        User user = this.userService.getByUsername(authentication.getName());

        return ResponseEntity.ok(new UserResponseDto(user));
    }
}
