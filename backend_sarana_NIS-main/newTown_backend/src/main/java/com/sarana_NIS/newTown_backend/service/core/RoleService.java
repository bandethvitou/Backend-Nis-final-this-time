package com.sarana_NIS.newTown_backend.service.core;

import com.sarana_NIS.newTown_backend.dto.request.core.RoleRequest;
import com.sarana_NIS.newTown_backend.dto.response.core.RoleResponse;
import com.sarana_NIS.newTown_backend.dto.response.core.RoleWithUsersResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.Role;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.enums.RoleCategory;
import com.sarana_NIS.newTown_backend.repository.RoleRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private static final Logger log = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public RoleResponse createRole(RoleRequest request) {
        if (roleRepository.findByName(request.getName()).isPresent()) {
            throw new RuntimeException("Role already exists: " + request.getName());
        }

        Role role = new Role();
        role.setName(request.getName());
        role.setDisplayName(request.getDisplayName());
        // ✅ Fix: Convert RoleCategory enum to String using name()
        if (request.getCategory() != null) {
            role.setCategory(request.getCategory().name());
        }
        role.setDescription(request.getDescription());
        role.setPermissions(request.getPermissions() != null ? request.getPermissions() : new ArrayList<>());
        role.setDashboardRoute(request.getDashboardRoute());

        Role saved = roleRepository.save(role);
        log.info("Role created: {}", saved.getName());
        return mapToResponse(saved);
    }

    @Transactional
    public RoleResponse updateRole(Long roleId, RoleRequest request) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        if (request.getName() != null) role.setName(request.getName());
        if (request.getDisplayName() != null) role.setDisplayName(request.getDisplayName());
        // ✅ Fix: Convert RoleCategory enum to String using name()
        if (request.getCategory() != null) {
            role.setCategory(request.getCategory().name());
        }
        if (request.getDescription() != null) role.setDescription(request.getDescription());
        if (request.getPermissions() != null) role.setPermissions(request.getPermissions());
        if (request.getDashboardRoute() != null) role.setDashboardRoute(request.getDashboardRoute());

        Role updated = roleRepository.save(role);
        log.info("Role updated: {}", updated.getName());
        return mapToResponse(updated);
    }

    @Transactional
    public void assignRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.addRole(role);
        userRepository.save(user);
        log.info("Role {} assigned to user {}", role.getName(), user.getUsername());
    }

    @Transactional
    public void removeRoleFromUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.removeRole(role);
        userRepository.save(user);
        log.info("Role {} removed from user {}", role.getName(), user.getUsername());
    }

    public RoleResponse getRoleById(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return mapToResponse(role);
    }

    public RoleResponse getRoleByName(String name) {
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Role not found: " + name));
        return mapToResponse(role);
    }

    public List<RoleResponse> getRolesByCategory(RoleCategory category) {
        // ✅ Fix: Convert enum to String for repository query
        List<Role> roles = roleRepository.findRolesByCategoryOrdered(category.name());
        return roles.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public PageResponse<RoleResponse> getAllRoles(Pageable pageable) {
        Page<Role> page = roleRepository.findAll(pageable);
        PageResponse<RoleResponse> response = new PageResponse<>();
        response.setContent(page.getContent().stream().map(this::mapToResponse).collect(Collectors.toList()));
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        return response;
    }

    public List<RoleResponse> getRolesByUser(Long userId) {
        List<Role> roles = roleRepository.findRolesByUserId(userId);
        return roles.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public RoleWithUsersResponse getRoleWithUsers(Long roleId) {
        Role role = roleRepository.findByIdWithUsers(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        RoleWithUsersResponse response = new RoleWithUsersResponse();
        response.setRole(mapToResponse(role));

        List<RoleWithUsersResponse.UserSummary> users = role.getUsers().stream()
                .map(user -> {
                    RoleWithUsersResponse.UserSummary summary = new RoleWithUsersResponse.UserSummary();
                    summary.setId(user.getId());
                    summary.setUsername(user.getUsername());
                    summary.setFullName(user.getFullName());
                    summary.setEmail(user.getEmail());
                    summary.setStatus(user.getUserStatus() != null ? user.getUserStatus().name() : null);
                    summary.setLastLogin(user.getLastLogin());
                    summary.setAvatarUrl(user.getAvatarUrl());
                    return summary;
                })
                .collect(Collectors.toList());
        response.setUsers(users);
        response.setTotalUsers(users.size());

        long active = users.stream().filter(u -> "ACTIVE".equals(u.getStatus())).count();
        response.setActiveUsers((int) active);
        response.setInactiveUsers(users.size() - (int) active);

        return response;
    }

    public List<String> getAllRoleNames() {
        return roleRepository.findAllRoleNames();
    }

    public long countRolesByCategory(RoleCategory category) {
        // ✅ Fix: Convert enum to String for repository query
        return roleRepository.countByCategory(category.name());
    }

    @Transactional
    public void deleteRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        if (!role.getUsers().isEmpty()) {
            throw new RuntimeException("Cannot delete role with users assigned");
        }

        roleRepository.delete(role);
        log.info("Role deleted: {}", role.getName());
    }

    private RoleResponse mapToResponse(Role role) {
        RoleResponse response = new RoleResponse();
        response.setId(role.getId());
        response.setName(role.getName());
        response.setDisplayName(role.getDisplayName());
        // ✅ Fix: Convert String category back to RoleCategory enum for response
        if (role.getCategory() != null) {
            try {
                response.setCategory(RoleCategory.valueOf(role.getCategory()));
            } catch (IllegalArgumentException e) {
                response.setCategory(null);
            }
        }
        response.setDescription(role.getDescription());
        response.setPermissions(role.getPermissions() != null ? role.getPermissions() : new ArrayList<>());
        response.setDashboardRoute(role.getDashboardRoute());
        response.setUserCount(role.getUsers() != null ? role.getUsers().size() : 0);
        response.setCreatedAt(role.getCreatedAt());
        response.setCreatedBy(role.getCreatedBy() != null ? role.getCreatedBy().toString() : null);
        response.setUpdatedAt(role.getUpdatedAt());
        response.setUpdatedBy(role.getUpdatedBy() != null ? role.getUpdatedBy().toString() : null);
        return response;
    }
}