package com.rento.service.rentoservice.facade;

import com.rento.service.rentoservice.dto.auth.AuthRequestDto;
import com.rento.service.rentoservice.dto.auth.AuthResponseDto;
import com.rento.service.rentoservice.dto.user.UserRequestDto;
import com.rento.service.rentoservice.entity.user.User;
import com.rento.service.rentoservice.entity.user.UserDetailsWrapper;
import com.rento.service.rentoservice.service.UserService;
import com.rento.service.rentoservice.service.auth.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthFacade {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthFacade(UserService userService,
                      PasswordEncoder passwordEncoder,
                      JwtTokenService jwtTokenService,
                      AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<AuthResponseDto> register(UserRequestDto request) {
        User user = createEntity(request);

        user = this.userService.createUser(user);
        String token = this.jwtTokenService.generateToken(new UserDetailsWrapper(user));

        AuthResponseDto responseDto = new AuthResponseDto(token, true);
        return ResponseEntity.ok(responseDto);
    }

    public ResponseEntity<AuthResponseDto> auth(AuthRequestDto request) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = this.userService.getByUsername(request.getUsername());
        String token = this.jwtTokenService.generateToken(new UserDetailsWrapper(user));

        AuthResponseDto responseDto = new AuthResponseDto(token, true);
        return ResponseEntity.ok(responseDto);
    }

    public User createEntity(UserRequestDto request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(this.passwordEncoder.encode(request.getPassword()));
        return user;
    }
}
