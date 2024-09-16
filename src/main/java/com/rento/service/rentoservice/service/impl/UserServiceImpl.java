package com.rento.service.rentoservice.service.impl;

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
    public User createByAdmin(User user) {
        validateCreateUser(user);

        return this.repository.save(user);
    }

    @Transactional
    @Override
    public User update(String authUsername, User user) {
        if (!Objects.equals(authUsername, user.getUsername()) || !this.repository.existsByUsername(authUsername)) {
            throw new ValidationException("Invalid username!");
        }
        User dbUser = getByUsername(authUsername);

        validateUpdateUser(user, dbUser);

        dbUser.setName(user.getName());
        dbUser.setEmail(user.getEmail());
        dbUser.setPhone(user.getPhone());
        dbUser.setPassword(user.getPhone());
        if (StringUtils.isNotBlank(user.getPassword())) {
            dbUser.setPassword(user.getPassword());
        }

        return this.repository.save(dbUser);
    }

    @Transactional
    @Override
    public void deleteByUsername(String username) {
        User user = getByUsername(username);

        Set<UUID> transportIds = this.transportRepository.findAllByOwnerId(user.getId()).stream()
                .map(Transport::getId)
                .collect(Collectors.toSet());
        this.transportRepository.deleteAllById(transportIds);

        List<Transport> transports = this.transportRepository.findAllByRenterId(user.getId()).stream()
                .peek(transport -> {
                            transport.setRenter(null);
                            transport.setStatus(TransportStatus.SERVICE);
                        }
                ).toList();
        this.transportRepository.saveAll(transports);

        this.repository.deleteById(user.getId());
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

        if (StringUtils.isBlank(user.getPassword())) {
            throw new ValidationException("Invalid password!");
        }
    }

    private void validateUpdateUser(User user, User dbUser) {
        if (StringUtils.isBlank(user.getName())) {
            throw new ValidationException("Invalid name!");
        }

        if (StringUtils.isBlank(user.getEmail()) ||
                (!dbUser.getEmail().equals(user.getEmail()) && this.repository.existsByEmail(user.getEmail()))
        ) {
            throw new ValidationException("Invalid email!");
        }
    }
}
