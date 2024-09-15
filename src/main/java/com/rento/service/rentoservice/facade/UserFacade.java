package com.rento.service.rentoservice.facade;

import com.rento.service.rentoservice.dto.SimpleResponseDto;
import com.rento.service.rentoservice.dto.user.UserRequestDto;
import com.rento.service.rentoservice.dto.user.UserResponseDto;
import com.rento.service.rentoservice.dto.user.UserRoleRequestDto;
import com.rento.service.rentoservice.entity.user.User;
import com.rento.service.rentoservice.service.UserService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserFacade {

    private final UserService service;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserFacade(UserService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<List<UserResponseDto>> getAllUsersExceptCaller(Authentication authentication) {
        List<User> users = this.service.getAllUsersExceptCaller(authentication.getName());

        List<UserResponseDto> response = users.stream().map(UserResponseDto::new).toList();

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<UserResponseDto> getUser(Authentication authentication) {
        User user = this.service.getByUsername(authentication.getName());

        return ResponseEntity.ok(new UserResponseDto(user));
    }

    public ResponseEntity<SimpleResponseDto> updateUser(Authentication authentication, UserRequestDto request) {
        this.service.update(authentication.getName(), createEntity(request));

        return ResponseEntity.ok().body(new SimpleResponseDto(true, "entity updated!"));
    }

    public ResponseEntity<SimpleResponseDto> updateRole(UserRoleRequestDto request) {
        this.service.updateRole(request.getUsername(), request.getRole());

        return ResponseEntity.ok().body(new SimpleResponseDto(true, "entity updated!"));
    }

    public ResponseEntity<SimpleResponseDto> deleteUser(UUID userId) {
        this.service.delete(userId);

        return ResponseEntity.ok().body(new SimpleResponseDto(true, "entity was deleted!"));
    }

    private User createEntity(UserRequestDto request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        String password = StringUtils.isBlank(request.getPassword()) ? ""
                : this.passwordEncoder.encode(request.getPassword());
        user.setPassword(password);

        return user;
    }
}
