package com.rento.service.rentoservice.entity.role;

import com.rento.service.rentoservice.entity.BaseEntity;
import com.rento.service.rentoservice.utils.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@Entity
@Table(schema = Constants.DataBase.SCHEMA, name = Constants.DataBase.ROLE_TABLE)
public class Role extends BaseEntity implements GrantedAuthority {

    @Column(name = "name", unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return name.equals(role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}