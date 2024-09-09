package com.rento.service.rentoservice.service.impl;

import com.rento.service.rentoservice.entity.role.Role;
import com.rento.service.rentoservice.exception.RoleNotFoundException;
import com.rento.service.rentoservice.repository.RoleRepository;
import com.rento.service.rentoservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Autowired
    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public Role getByName(String name) {
        return this.repository.findByNameIgnoreCase(name).orElseThrow(
                () -> new RoleNotFoundException(name)
        );
    }
}
