package com.rento.service.rentoservice.service;

import com.rento.service.rentoservice.entity.user.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsersExceptCaller(String callerUsername);

    User getByUsername(String username);

    User create(User user);

    User createByAdmin(User user);

    User update(String authUsername, User user);

    void deleteByUsername(String username);
}
