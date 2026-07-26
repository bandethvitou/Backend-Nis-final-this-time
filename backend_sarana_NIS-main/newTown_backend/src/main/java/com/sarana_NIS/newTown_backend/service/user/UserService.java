package com.sarana_NIS.newTown_backend.service.user;

import com.sarana_NIS.newTown_backend.dto.request.user.UserCreateRequest;
import com.sarana_NIS.newTown_backend.dto.request.user.UserUpdateRequest;
import com.sarana_NIS.newTown_backend.dto.request.user.UserSearchRequest;
import com.sarana_NIS.newTown_backend.dto.request.user.BulkUserActionRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.dto.response.user.UserResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.Role;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.enums.UserStatus;
import com.sarana_NIS.newTown_backend.model.enums.UserRole;
import com.sarana_NIS.newTown_backend.repository.RoleRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ============================================================
    // CREATE
    // ============================================================

    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists: " + request.getUsername());
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setUserStatus(UserStatus.ACTIVE);
        user.setIsEmailVerified(false);

        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
            Set<Role> roles = roleRepository.findAllByIdIn(request.getRoleIds().stream().toList())
                    .stream()
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        User saved = userRepository.save(user);
        log.info("User created: {}", saved.getUsername());
        return mapToResponse(saved);
    }

    // ============================================================
    // READ
    // ============================================================

    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return mapToResponse(user);
    }

    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        return mapToResponse(user);
    }

    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return mapToResponse(user);
    }

    public PageResponse<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        return mapToPageResponse(page);
    }

    public List<UserResponse> getUsersByRole(String roleName) {
        UserRole role;
        try {
            role = UserRole.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + roleName);
        }
        List<User> users = userRepository.findByRole(role);
        return users.stream()
                .map(UserService::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<UserResponse> getUsersByStatus(UserStatus status) {
        List<User> users = userRepository.findByUserStatus(status);
        return users.stream()
                .map(UserService::mapToResponse)
                .collect(Collectors.toList());
    }

    public PageResponse<UserResponse> searchUsers(UserSearchRequest request) {
        Pageable pageable = Pageable.ofSize(request.getSize()).withPage(request.getPage());
        Page<User> page = userRepository.findAll(pageable);
        return mapToPageResponse(page);
    }

    // ============================================================
    // UPDATE
    // ============================================================

    @Transactional
    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPhone() != null) user.setPhone(request.getPhone());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getRoleIds() != null) {
            Set<Role> roles = roleRepository.findAllByIdIn(request.getRoleIds().stream().toList())
                    .stream()
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        User updated = userRepository.save(user);
        log.info("User updated: {}", updated.getUsername());
        return mapToResponse(updated);
    }

    @Transactional
    public UserResponse updateUserStatus(Long userId, UserStatus status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        user.setUserStatus(status);
        User updated = userRepository.save(user);
        log.info("User status updated: {} -> {}", userId, status);
        return mapToResponse(updated);
    }

    @Transactional
    public UserResponse changeUserPassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        User updated = userRepository.save(user);
        log.info("Password changed for user: {}", userId);
        return mapToResponse(updated);
    }

    // ============================================================
    // BULK ACTIONS
    // ============================================================

    @Transactional
    public void bulkAction(BulkUserActionRequest request) {
        List<User> users = userRepository.findAllById(request.getUserIds());

        switch (request.getAction()) {
            case "ACTIVATE":
                users.forEach(u -> u.setUserStatus(UserStatus.ACTIVE));
                break;
            case "DEACTIVATE":
                users.forEach(u -> u.setUserStatus(UserStatus.INACTIVE));
                break;
            case "LOCK":
                users.forEach(u -> u.setUserStatus(UserStatus.LOCKED));
                break;
            case "ASSIGN_ROLE":
                if (request.getRoleId() == null) {
                    throw new RuntimeException("Role ID is required for ASSIGN_ROLE action");
                }
                Role role = roleRepository.findById(request.getRoleId())
                        .orElseThrow(() -> new RuntimeException("Role not found"));
                users.forEach(u -> u.getRoles().add(role));
                break;
            case "REMOVE_ROLE":
                if (request.getRoleId() == null) {
                    throw new RuntimeException("Role ID is required for REMOVE_ROLE action");
                }
                Role roleToRemove = roleRepository.findById(request.getRoleId())
                        .orElseThrow(() -> new RuntimeException("Role not found"));
                users.forEach(u -> u.getRoles().remove(roleToRemove));
                break;
            default:
                throw new RuntimeException("Invalid bulk action: " + request.getAction());
        }

        userRepository.saveAll(users);
        log.info("Bulk action '{}' completed on {} users", request.getAction(), users.size());
    }

    // ============================================================
    // DELETE
    // ============================================================

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        user.setUserStatus(UserStatus.DELETED);
        userRepository.save(user);
        log.info("User soft deleted: {}", userId);
    }

    @Transactional
    public void hardDeleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        userRepository.delete(user);
        log.info("User hard deleted: {}", userId);
    }

    // ============================================================
    // HELPER METHODS - ALL STATIC
    // ============================================================

    public static UserResponse mapToUserResponse(User user) {
        return mapToResponse(user);
    }

    private static UserResponse mapToResponse(User user) {
        if (user == null) return null;

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if (user.getUserStatus() != null) {
            response.setUserStatus(user.getUserStatus().name());
        }

        // ✅ FIXED: Use avatarUrl instead of profilePicture
        response.setProfilePicture(user.getAvatarUrl());
        response.setAvatarUrl(user.getAvatarUrl());
        response.setLastLogin(user.getLastLogin());
        response.setIsEmailVerified(user.getIsEmailVerified());

        // ✅ FIXED: User entity doesn't have isPhoneVerified, set default
        response.setIsPhoneVerified(false);

        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());

        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            List<UserResponse.RoleInfo> roleInfos = user.getRoles().stream()
                    .map(role -> {
                        UserResponse.RoleInfo info = new UserResponse.RoleInfo();
                        info.setId(role.getId());
                        info.setName(role.getName());
                        info.setDisplayName(role.getDisplayName());
                        info.setCategory(role.getCategory());
                        info.setDashboardRoute(role.getDashboardRoute());
                        return info;
                    })
                    .collect(Collectors.toList());
            response.setRoles(roleInfos);
        }

        return response;
    }

    private PageResponse<UserResponse> mapToPageResponse(Page<User> page) {
        PageResponse<UserResponse> response = new PageResponse<>();
        response.setContent(page.getContent().stream()
                .map(UserService::mapToResponse)
                .collect(Collectors.toList()));
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLast(page.isLast());
        response.setFirst(page.isFirst());
        return response;
    }
}