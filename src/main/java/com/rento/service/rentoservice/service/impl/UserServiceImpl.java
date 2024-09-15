package com.rento.service.rentoservice.service.impl;

import com.rento.service.rentoservice.entity.role.Role;
import com.rento.service.rentoservice.entity.transport.Transport;
import com.rento.service.rentoservice.entity.transport.TransportStatus;
import com.rento.service.rentoservice.entity.user.User;
import com.rento.service.rentoservice.exception.UserNotFoundException;
import com.rento.service.rentoservice.exception.ValidationException;
import com.rento.service.rentoservice.repository.TransportRepository;
import com.rento.service.rentoservice.repository.UserRepository;
import com.rento.service.rentoservice.service.RoleService;
import com.rento.service.rentoservice.service.UserService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final TransportRepository transportRepository;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(TransportRepository transportRepository, UserRepository repository, RoleService roleService) {
        this.transportRepository = transportRepository;
        this.repository = repository;
        this.roleService = roleService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsersExceptCaller(String callerUsername) {
        return this.repository.findAllByUsernameNot(callerUsername);
    }

    @Transactional(readOnly = true)
    @Override
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

    @Transactional
    @Override
    public void update(String authUsername, User user) {
        validateUpdateUser(authUsername, user);

        User dbUser = getByUsername(authUsername);

        dbUser.setName(user.getName());
        dbUser.setEmail(user.getEmail());
        dbUser.setPassword(user.getPhone());
        if (StringUtils.isNotBlank(user.getPassword())) {
            dbUser.setPassword(user.getPassword());
        }

        this.repository.save(dbUser);
    }

    @Transactional
    @Override
    public void updateRole(String username, String role) {
        validateUpdateRole(username, role);

        User dbUser = getByUsername(username);
        Role newRole = this.roleService.getByName(role);
        dbUser.setRoles(Set.of(newRole));

        this.repository.save(dbUser);
    }

    @Transactional
    @Override
    public void delete(UUID userId) {
        Set<UUID> transportIds = this.transportRepository.findAllByOwnerId(userId).stream()
                .map(Transport::getId)
                .collect(Collectors.toSet());
        this.transportRepository.deleteAllById(transportIds);

        List<Transport> transports = this.transportRepository.findAllByRenterId(userId).stream()
                .peek(transport -> {
                            transport.setRenter(null);
                            transport.setStatus(TransportStatus.SERVICE);
                        }
                ).toList();
        this.transportRepository.saveAll(transports);

        this.repository.deleteById(userId);
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

    private void validateUpdateUser(String authUsername, User user) {
        if (!Objects.equals(authUsername, user.getUsername()) || !this.repository.existsByUsername(authUsername)) {
            throw new ValidationException("Invalid username!");
        }

        if (StringUtils.isBlank(user.getName())) {
            throw new ValidationException("Invalid name!");
        }

        if (StringUtils.isBlank(user.getEmail()) || this.repository.existsByEmail(user.getEmail())) {
            throw new ValidationException("Invalid email!");
        }
    }

    private void validateUpdateRole(String username, String role) {
        if (!this.repository.existsByUsername(username)) {
            throw new ValidationException("Invalid username!");
        }

        if (!"user".equals(role) && !"admin".equals(role)) {
            throw new ValidationException("Invalid role!");
        }
    }
}
