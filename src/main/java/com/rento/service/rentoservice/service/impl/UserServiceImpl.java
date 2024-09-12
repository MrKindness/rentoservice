package com.rento.service.rentoservice.service.impl;

import com.rento.service.rentoservice.entity.user.User;
import com.rento.service.rentoservice.exception.UserNotFoundException;
import com.rento.service.rentoservice.exception.ValidationException;
import com.rento.service.rentoservice.repository.UserRepository;
import com.rento.service.rentoservice.service.RoleService;
import com.rento.service.rentoservice.service.UserService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository repository, RoleService roleService) {
        this.repository = repository;
        this.roleService = roleService;
    }

    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    @Transactional
    @Override
    public User create(User user) {
        user.setRoles(Set.of(this.roleService.getByName("user")));

        validateCreateUser(user);

        return this.repository.save(user);
    }

    private void validateCreateUser(User user) {
        if (Objects.nonNull(user.getId())) {
            throw new ValidationException("Invalid id!");
        }

        if (StringUtils.isBlank(user.getUsername()) || this.repository.existsByUsername(user.getUsername())) {
            throw new ValidationException("Invalid username!");
        }

        if (StringUtils.isBlank(user.getName())) {
            throw new ValidationException("Invalid name!");
        }

        if (StringUtils.isBlank(user.getEmail()) || this.repository.existsByEmail(user.getEmail())) {
            throw new ValidationException("Invalid email!");
        }
    }
}
