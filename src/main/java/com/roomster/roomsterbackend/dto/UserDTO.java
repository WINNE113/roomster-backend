package com.roomster.roomsterbackend.dto;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class UserDTO implements UserDetails {
    private Long id;

    private String userName;

    private String passwordHash;

    private String email;

    private String phoneNumber;

    private int twoFactorEnable;

    private boolean isActive;

    private boolean isDeleted;

    private Date dateOfBirth;

    private String address;

    @Enumerated(EnumType.STRING)
    private RoleDTO role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
