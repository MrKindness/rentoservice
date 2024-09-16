package com.rento.service.rentoservice.facade;

import com.rento.service.rentoservice.dto.SimpleResponseDto;
import com.rento.service.rentoservice.dto.user.UserRequestDto;
import com.rento.service.rentoservice.dto.user.UserResponseDto;
import com.rento.service.rentoservice.entity.user.User;
import com.rento.service.rentoservice.service.RoleService;
import com.rento.service.rentoservice.service.UserService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class UserFacade {

    private final UserService service;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserFacade(UserService service, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.roleService = roleService;
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

    public ResponseEntity<SimpleResponseDto> createUserByAdmin(UserRequestDto request) {
        User user = createEntity(request);
        user.setPassword(this.passwordEncoder.encode(request.getUsername()));
        user.setRoles(Set.of(this.roleService.getByName(request.getRole())));

        this.service.createByAdmin(user);

        return ResponseEntity.ok().body(new SimpleResponseDto(true, "entity was created!"));
    }

    public ResponseEntity<UserResponseDto> updateUser(Authentication authentication, UserRequestDto request) {
        User updatedUser = this.service.update(authentication.getName(), createEntity(request));

        return ResponseEntity.ok().body(new UserResponseDto(updatedUser));
    }

    public ResponseEntity<SimpleResponseDto> deleteUser(String username) {
        this.service.deleteByUsername(username);

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
