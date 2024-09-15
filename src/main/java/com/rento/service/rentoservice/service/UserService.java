package com.rento.service.rentoservice.service;

import com.rento.service.rentoservice.entity.user.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getAllUsersExceptCaller(String callerUsername);

    User getByUsername(String username);

    User create(User user);

    void update(String authUsername, User user);

    void updateRole(String username, String role);

    void delete(UUID userId);
}
