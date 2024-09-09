package com.rento.service.rentoservice.service;

import com.rento.service.rentoservice.entity.user.User;

public interface UserService {
    User getByUsername(String username);

    User createUser(User user);
}
