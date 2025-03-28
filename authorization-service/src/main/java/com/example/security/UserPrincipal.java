package com.example.security;

import com.example.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@JsonDeserialize
public class UserPrincipal implements UserDetails {
    private final static String USER_ROLE_PREFIX = "ROLE_";
    private final UserEntity user;
    private Collection<? extends GrantedAuthority> authorities;
    public UserPrincipal(UserEntity user) {
        this.user = user;
        this.authorities = Collections.singleton(new SimpleGrantedAuthority(USER_ROLE_PREFIX + user.getRole().name()));
    }
    @JsonCreator
    public UserPrincipal(
            @JsonProperty("user") UserEntity user,
            @JsonProperty("authorities") Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities != null ? authorities : Collections.singleton(new SimpleGrantedAuthority(USER_ROLE_PREFIX + user.getRole().name()));
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE_PREFIX + user.getRole().name()));
        return authorities;
    }
    @JsonIgnore
    public long getId() {
        return user.getId();
    }
    @JsonIgnore
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
    @Override
    public String toString() {
        return this.getUsername();
    }

    public UserEntity getUser() {
        return user;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}

