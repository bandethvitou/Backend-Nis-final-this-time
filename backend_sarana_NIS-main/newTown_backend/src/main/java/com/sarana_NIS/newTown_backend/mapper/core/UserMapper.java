package com.sarana_NIS.newTown_backend.mapper.core;

import com.sarana_NIS.newTown_backend.dto.request.user.UserCreateRequest;
import com.sarana_NIS.newTown_backend.dto.request.user.UserUpdateRequest;
import com.sarana_NIS.newTown_backend.dto.response.user.UserResponse;
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
public interface UserMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    @Mapping(target = "userStatus", expression = "java(user.getUserStatus() != null ? user.getUserStatus().name() : null)")
    @Mapping(target = "profilePicture", source = "avatarUrl")
    @Mapping(target = "isPhoneVerified", constant = "false")
    UserResponse toResponse(User user);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "passwordHash", source = "password")
    @Mapping(target = "userStatus", constant = "ACTIVE")
    @Mapping(target = "isEmailVerified", constant = "false")
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "staff", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "accountLocked", ignore = true)
    @Mapping(target = "lockoutTime", ignore = true)
    @Mapping(target = "failedLoginAttempts", ignore = true)
    // ✅ REMOVED: profilePicture and isPhoneVerified don't exist in User entity
    @Mapping(target = "avatarUrl", ignore = true)
    User toEntity(UserCreateRequest request);

    // ========== Update existing entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "userStatus", ignore = true)
    @Mapping(target = "isEmailVerified", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "staff", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "accountLocked", ignore = true)
    @Mapping(target = "lockoutTime", ignore = true)
    @Mapping(target = "failedLoginAttempts", ignore = true)
    // ✅ REMOVED: profilePicture and isPhoneVerified don't exist in User entity
    @Mapping(target = "avatarUrl", ignore = true)
    void updateEntity(UserUpdateRequest request, @MappingTarget User user);

    // ========== Helper method for roles ==========
    default List<UserResponse.RoleInfo> mapRoles(Set<Role> roles) {
        if (roles == null) return new ArrayList<>();
        return roles.stream()
                .map(r -> {
                    UserResponse.RoleInfo info = new UserResponse.RoleInfo();
                    info.setId(r.getId());
                    info.setName(r.getName());
                    info.setDisplayName(r.getDisplayName());
                    info.setCategory(r.getCategory());
                    info.setDashboardRoute(r.getDashboardRoute());
                    return info;
                })
                .collect(Collectors.toList());
    }

    // ========== Map single role ==========
    default UserResponse.RoleInfo mapRole(Role role) {
        if (role == null) return null;
        UserResponse.RoleInfo info = new UserResponse.RoleInfo();
        info.setId(role.getId());
        info.setName(role.getName());
        info.setDisplayName(role.getDisplayName());
        info.setCategory(role.getCategory());
        info.setDashboardRoute(role.getDashboardRoute());
        return info;
    }
}