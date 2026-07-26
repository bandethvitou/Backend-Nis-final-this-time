package com.sarana_NIS.newTown_backend.controller.core;

import com.sarana_NIS.newTown_backend.dto.request.core.RoleRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.dto.response.core.RoleResponse;
import com.sarana_NIS.newTown_backend.dto.response.core.RoleWithUsersResponse;
import com.sarana_NIS.newTown_backend.model.enums.RoleCategory;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.core.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Role Management", description = "Role management APIs")
public class RoleController {

    private static final Logger log = LoggerFactory.getLogger(RoleController.class);

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @RequiresRole("DIRECTOR")
    @Operation(summary = "Get all roles")
    public ResponseEntity<ApiResponse<PageResponse<RoleResponse>>> getAllRoles(Pageable pageable) {
        log.info("Fetching all roles");
        PageResponse<RoleResponse> response = roleService.getAllRoles(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    @RequiresRole("DIRECTOR")
    @Operation(summary = "Get role by ID")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleById(@PathVariable Long id) {
        log.info("Fetching role: {}", id);
        RoleResponse response = roleService.getRoleById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/name/{name}")
    @RequiresRole("DIRECTOR")
    @Operation(summary = "Get role by name")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleByName(@PathVariable String name) {
        log.info("Fetching role by name: {}", name);
        RoleResponse response = roleService.getRoleByName(name);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/category/{category}")
    @RequiresRole("DIRECTOR")
    @Operation(summary = "Get roles by category")
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getRolesByCategory(
            @PathVariable RoleCategory category) {
        log.info("Fetching roles by category: {}", category);
        List<RoleResponse> response = roleService.getRolesByCategory(category);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/user/{userId}")
    @RequiresRole("DIRECTOR")
    @Operation(summary = "Get roles by user")
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getRolesByUser(@PathVariable Long userId) {
        log.info("Fetching roles by user: {}", userId);
        List<RoleResponse> response = roleService.getRolesByUser(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}/users")
    @RequiresRole("DIRECTOR")
    @Operation(summary = "Get role with users")
    public ResponseEntity<ApiResponse<RoleWithUsersResponse>> getRoleWithUsers(@PathVariable Long id) {
        log.info("Fetching role with users: {}", id);
        RoleWithUsersResponse response = roleService.getRoleWithUsers(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    @RequiresRole("DIRECTOR")
    @RequiresPermission("ROLE_CREATE")
    @Operation(summary = "Create a new role")
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(
            @Valid @RequestBody RoleRequest request) {
        log.info("Creating role: {}", request.getName());
        RoleResponse response = roleService.createRole(request);
        return ResponseEntity.ok(ApiResponse.success("Role created successfully", response));
    }

    @PutMapping("/{id}")
    @RequiresRole("DIRECTOR")
    @RequiresPermission("ROLE_UPDATE")
    @Operation(summary = "Update role")
    public ResponseEntity<ApiResponse<RoleResponse>> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleRequest request) {
        log.info("Updating role: {}", id);
        RoleResponse response = roleService.updateRole(id, request);
        return ResponseEntity.ok(ApiResponse.success("Role updated successfully", response));
    }

    @PostMapping("/{roleId}/assign/{userId}")
    @RequiresRole("DIRECTOR")
    @RequiresPermission("ROLE_ASSIGN")
    @Operation(summary = "Assign role to user")
    public ResponseEntity<ApiResponse<Void>> assignRoleToUser(
            @PathVariable Long roleId,
            @PathVariable Long userId) {
        log.info("Assigning role {} to user {}", roleId, userId);
        roleService.assignRoleToUser(userId, roleId);
        return ResponseEntity.ok(ApiResponse.success("Role assigned successfully", null));
    }

    @PostMapping("/{roleId}/remove/{userId}")
    @RequiresRole("DIRECTOR")
    @RequiresPermission("ROLE_REMOVE")
    @Operation(summary = "Remove role from user")
    public ResponseEntity<ApiResponse<Void>> removeRoleFromUser(
            @PathVariable Long roleId,
            @PathVariable Long userId) {
        log.info("Removing role {} from user {}", roleId, userId);
        roleService.removeRoleFromUser(userId, roleId);
        return ResponseEntity.ok(ApiResponse.success("Role removed successfully", null));
    }

    @DeleteMapping("/{id}")
    @RequiresRole("DIRECTOR")
    @RequiresPermission("ROLE_DELETE")
    @Operation(summary = "Delete role")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable Long id) {
        log.info("Deleting role: {}", id);
        roleService.deleteRole(id);
        return ResponseEntity.ok(ApiResponse.success("Role deleted successfully", null));
    }
}