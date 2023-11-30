package com.travelsphere.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public enum UserRole {
    ROLE_USER("ROLE_USER",
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

    private final String roleName;
    private final List<GrantedAuthority> authorities;
}