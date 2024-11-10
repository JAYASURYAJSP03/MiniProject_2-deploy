package com.Task.MiniProject_2.secirityConfig;

import com.Task.MiniProject_2.entity.securityEntity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Grant authority based on the role field
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // You can add expiry logic later if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // You can add locking logic later if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // You can add credentials expiry logic later if needed
    }

    @Override
    public boolean isEnabled() {
        return true;  // You can add enable/disable logic later if needed
    }
}

