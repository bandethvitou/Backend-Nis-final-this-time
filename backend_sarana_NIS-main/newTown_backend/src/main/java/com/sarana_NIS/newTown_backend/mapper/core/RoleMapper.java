package com.sarana_NIS.newTown_backend.mapper.core;

import com.sarana_NIS.newTown_backend.dto.request.core.RoleRequest;
import com.sarana_NIS.newTown_backend.dto.response.core.RoleResponse;
import com.sarana_NIS.newTown_backend.dto.response.core.RoleWithUsersResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.Role;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RoleMapper {

    // Entity → Response
    @Mapping(target = "userCount", expression = "java(role.getUsers() != null ? role.getUsers().size() : 0)")
    @Mapping(target = "permissions", expression = "java(mapPermissions(role.getPermissions()))")
    RoleResponse toResponse(Role role);

    // RoleWithUsersResponse
    @Mapping(target = "role", expression = "java(toResponse(role))")
    @Mapping(target = "users", expression = "java(mapUsers(role.getUsers()))")
    @Mapping(target = "totalUsers", expression = "java(role.getUsers() != null ? role.getUsers().size() : 0)")
    @Mapping(target = "activeUsers", expression = "java(countActiveUsers(role.getUsers()))")
    @Mapping(target = "inactiveUsers", expression = "java(role.getUsers() != null ? role.getUsers().size() - countActiveUsers(role.getUsers()) : 0)")
    RoleWithUsersResponse toRoleWithUsersResponse(Role role);

    // Request → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "permissions", expression = "java(mapPermissions(request.getPermissions()))")
    Role toEntity(RoleRequest request);

    // Update
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "permissions", expression = "java(mapPermissions(request.getPermissions()))")
    void updateEntity(RoleRequest request, @MappingTarget Role role);

    // Helper methods
    default List<String> mapPermissions(String permissions) {
        if (permissions == null || permissions.isEmpty()) {
            return new ArrayList<>();
        }
        // If permissions is a comma-separated string, split it
        if (permissions.contains(",")) {
            String[] parts = permissions.split(",");
            List<String> result = new ArrayList<>();
            for (String part : parts) {
                String trimmed = part.trim();
                if (!trimmed.isEmpty()) {
                    result.add(trimmed);
                }
            }
            return result;
        }
        // If it's a single permission
        List<String> result = new ArrayList<>();
        result.add(permissions);
        return result;
    }

    default List<String> mapPermissions(List<String> permissions) {
        if (permissions == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(permissions);
    }

    default List<RoleWithUsersResponse.UserSummary> mapUsers(Set<User> users) {
        if (users == null) return new ArrayList<>();
        return users.stream()
                .map(u -> {
                    RoleWithUsersResponse.UserSummary summary = new RoleWithUsersResponse.UserSummary();
                    summary.setId(u.getId());
                    summary.setUsername(u.getUsername());
                    summary.setFullName(u.getFullName());
                    summary.setEmail(u.getEmail());

                    // Use getUserStatus() method
                    if (u.getUserStatus() != null) {
                        summary.setStatus(u.getUserStatus().name());
                    }

                    summary.setLastLogin(u.getLastLogin());
                    summary.setAvatarUrl(u.getAvatarUrl());
                    return summary;
                })
                .collect(Collectors.toList());
    }

    default int countActiveUsers(Set<User> users) {
        if (users == null) return 0;
        return (int) users.stream()
                .filter(u -> u.getUserStatus() != null && u.getUserStatus().name().equals("ACTIVE"))
                .count();
    }
}