package com.sarana_NIS.newTown_backend.security.service;

import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        if (user.getUserStatus() == null || user.getUserStatus().name().equals("INACTIVE") ||
                user.getUserStatus().name().equals("SUSPENDED")) {
            throw new UsernameNotFoundException("User account is not active");
        }

        return CustomUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPasswordHash())
                .fullName(user.getFullName())
                .roles(user.getRoles().stream()
                        .map(role -> "ROLE_" + role.getName())
                        .toList())
                .userStatus(user.getUserStatus())
                .isEmailVerified(user.getIsEmailVerified())
                .build();
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        return CustomUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPasswordHash())
                .fullName(user.getFullName())
                .roles(user.getRoles().stream()
                        .map(role -> "ROLE_" + role.getName())
                        .toList())
                .userStatus(user.getUserStatus())
                .isEmailVerified(user.getIsEmailVerified())
                .build();
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return CustomUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPasswordHash())
                .fullName(user.getFullName())
                .roles(user.getRoles().stream()
                        .map(role -> "ROLE_" + role.getName())
                        .toList())
                .userStatus(user.getUserStatus())
                .isEmailVerified(user.getIsEmailVerified())
                .build();
    }
}