package com.example.TimeTracker.security.services;

import com.example.TimeTracker.model.Gender;
import com.example.TimeTracker.model.User;
import com.example.TimeTracker.model.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;

import java.util.Objects;


@Getter
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String email;
    @JsonIgnore
    private String password;
    private String fullName;
    private String department;
    private String position;
    private UserRole userRole;
    private String leaderEmail;
    private Gender gender;
    private Date hireDate;


    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id,
                           String email,
                           String password,
                           String fullName,
                           String department,
                           String position,
                           UserRole userRole,
                           String leaderEmail,
                           Gender gender,
                           Date hireDate,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.department = department;
        this.position = position;
        this.userRole = userRole;
        this.leaderEmail = leaderEmail;
        this.authorities = authorities;
        this.hireDate = hireDate;
        this.gender = gender;
    }

    public static UserDetailsImpl build(User user) {
        SimpleGrantedAuthority authorities =
                new SimpleGrantedAuthority(user.getRole().name());


        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getFullName(),
                user.getDepartment(),
                user.getPosition(),
                user.getRole(),
                user.getLeaderEmail(),
                user.getGender(),
                user.getHireDate(),
                Collections.singletonList(authorities));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}