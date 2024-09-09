package com.rento.service.rentoservice.service;

import com.rento.service.rentoservice.entity.role.Role;

public interface RoleService {
    Role getByName(String name);
}
