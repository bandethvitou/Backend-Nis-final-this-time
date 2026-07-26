package com.sarana_NIS.newTown_backend.security.service;

import com.sarana_NIS.newTown_backend.model.enums.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private List<String> roles;
    private UserStatus userStatus;
    private Boolean isEmailVerified;

    // Private constructor for builder
    private CustomUserDetails() {}

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String username;
        private String email;
        private String password;
        private String fullName;
        private List<String> roles;
        private UserStatus userStatus;
        private Boolean isEmailVerified;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder username(String username) { this.username = username; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Builder fullName(String fullName) { this.fullName = fullName; return this; }
        public Builder roles(List<String> roles) { this.roles = roles; return this; }
        public Builder userStatus(UserStatus userStatus) { this.userStatus = userStatus; return this; }
        public Builder isEmailVerified(Boolean isEmailVerified) { this.isEmailVerified = isEmailVerified; return this; }

        public CustomUserDetails build() {
            CustomUserDetails details = new CustomUserDetails();
            details.id = this.id;
            details.username = this.username;
            details.email = this.email;
            details.password = this.password;
            details.fullName = this.fullName;
            details.roles = this.roles;
            details.userStatus = this.userStatus;
            details.isEmailVerified = this.isEmailVerified;
            return details;
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    public UserStatus getUserStatus() { return userStatus; }
    public void setUserStatus(UserStatus userStatus) { this.userStatus = userStatus; }

    public Boolean getIsEmailVerified() { return isEmailVerified; }
    public void setIsEmailVerified(Boolean isEmailVerified) { this.isEmailVerified = isEmailVerified; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return userStatus != UserStatus.SUSPENDED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userStatus == UserStatus.ACTIVE;
    }

    public boolean isEmailVerified() {
        return isEmailVerified != null && isEmailVerified;
    }

    public boolean hasRole(String role) {
        return roles.contains("ROLE_" + role) || roles.contains(role);
    }

    public boolean hasAnyRole(String... roles) {
        for (String role : roles) {
            if (hasRole(role)) {
                return true;
            }
        }
        return false;
    }
}