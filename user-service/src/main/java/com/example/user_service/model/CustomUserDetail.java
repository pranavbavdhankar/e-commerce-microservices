package com.example.user_service.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetail implements UserDetails {


    private final UserPrincipal userPrincipal;

    public CustomUserDetail(UserPrincipal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + userPrincipal.getRole()));
    }

    @Override
    public String getPassword() {
        return userPrincipal.getPassword();
    }

    @Override
    public String getUsername() {
        return userPrincipal.getEmail();
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
        return userPrincipal.isActive();
    }
}
