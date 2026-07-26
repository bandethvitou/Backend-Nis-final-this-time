package com.sarana_NIS.newTown_backend.controller.core;

import com.sarana_NIS.newTown_backend.dto.request.user.UserCreateRequest;
import com.sarana_NIS.newTown_backend.dto.request.user.UserUpdateRequest;
import com.sarana_NIS.newTown_backend.dto.request.user.UserSearchRequest;
import com.sarana_NIS.newTown_backend.dto.request.user.BulkUserActionRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.dto.response.user.UserResponse;
import com.sarana_NIS.newTown_backend.model.enums.UserStatus;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "User management APIs")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ============================================================
    // CREATE
    // ============================================================

    @PostMapping
    @RequiresRole({"DIRECTOR"})
    @RequiresPermission("USER_CREATE")
    @Operation(summary = "Create a new user")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody UserCreateRequest request) {
        System.out.println("Creating user: " + request.getUsername());
        UserResponse response = userService.createUser(request);
        return ResponseEntity.ok(ApiResponse.success("User created successfully", response));
    }

    // ============================================================
    // READ - ALL
    // ============================================================

    @GetMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get all users with pagination")
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> getAllUsers(
            Pageable pageable) {
        System.out.println("Fetching all users");
        PageResponse<UserResponse> response = userService.getAllUsers(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // ============================================================
    // READ - BY ID
    // ============================================================

    @GetMapping("/{userId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get user by ID")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @PathVariable Long userId) {
        System.out.println("Fetching user: " + userId);
        UserResponse response = userService.getUserById(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // ============================================================
    // READ - BY USERNAME
    // ============================================================

    @GetMapping("/username/{username}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get user by username")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByUsername(
            @PathVariable String username) {
        System.out.println("Fetching user by username: " + username);
        UserResponse response = userService.getUserByUsername(username);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // ============================================================
    // READ - BY EMAIL
    // ============================================================

    @GetMapping("/email/{email}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get user by email")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByEmail(
            @PathVariable String email) {
        System.out.println("Fetching user by email: " + email);
        UserResponse response = userService.getUserByEmail(email);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // ============================================================
    // READ - BY ROLE
    // ============================================================

    @GetMapping("/role/{roleName}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get users by role")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsersByRole(
            @PathVariable String roleName) {
        System.out.println("Fetching users by role: " + roleName);
        List<UserResponse> responses = userService.getUsersByRole(roleName);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    // ============================================================
    // READ - BY STATUS
    // ============================================================

    @GetMapping("/status/{status}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get users by status")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsersByStatus(
            @PathVariable UserStatus status) {
        System.out.println("Fetching users by status: " + status);
        List<UserResponse> responses = userService.getUsersByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    // ============================================================
    // READ - SEARCH
    // ============================================================

    @PostMapping("/search")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Search users")
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> searchUsers(
            @Valid @RequestBody UserSearchRequest request) {
        System.out.println("Searching users with keyword: " + request.getKeyword());
        PageResponse<UserResponse> response = userService.searchUsers(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // ============================================================
    // UPDATE
    // ============================================================

    @PutMapping("/{userId}")
    @RequiresRole({"DIRECTOR"})
    @RequiresPermission("USER_UPDATE")
    @Operation(summary = "Update user")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UserUpdateRequest request) {
        System.out.println("Updating user: " + userId);
        UserResponse response = userService.updateUser(userId, request);
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", response));
    }

    // ============================================================
    // UPDATE - STATUS
    // ============================================================

    @PatchMapping("/{userId}/status")
    @RequiresRole({"DIRECTOR"})
    @RequiresPermission("USER_UPDATE")
    @Operation(summary = "Update user status")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserStatus(
            @PathVariable Long userId,
            @RequestParam UserStatus status) {
        System.out.println("Updating user status: " + userId + " -> " + status);
        UserResponse response = userService.updateUserStatus(userId, status);
        return ResponseEntity.ok(ApiResponse.success("User status updated successfully", response));
    }

    // ============================================================
    // UPDATE - PASSWORD
    // ============================================================

    @PatchMapping("/{userId}/password")
    @RequiresRole({"DIRECTOR"})
    @RequiresPermission("USER_UPDATE")
    @Operation(summary = "Change user password")
    public ResponseEntity<ApiResponse<UserResponse>> changeUserPassword(
            @PathVariable Long userId,
            @RequestParam String newPassword) {
        System.out.println("Changing password for user: " + userId);
        UserResponse response = userService.changeUserPassword(userId, newPassword);
        return ResponseEntity.ok(ApiResponse.success("Password changed successfully", response));
    }

    // ============================================================
    // BULK ACTION
    // ============================================================

    @PostMapping("/bulk")
    @RequiresRole({"DIRECTOR"})
    @RequiresPermission("USER_UPDATE")
    @Operation(summary = "Bulk action on users")
    public ResponseEntity<ApiResponse<Void>> bulkAction(
            @Valid @RequestBody BulkUserActionRequest request) {
        System.out.println("Bulk action: " + request.getAction() + " on " + request.getUserIds().size() + " users");
        userService.bulkAction(request);
        return ResponseEntity.ok(ApiResponse.success("Bulk action completed successfully", null));
    }

    // ============================================================
    // DELETE - SOFT
    // ============================================================

    @DeleteMapping("/{userId}")
    @RequiresRole({"DIRECTOR"})
    @RequiresPermission("USER_DELETE")
    @Operation(summary = "Soft delete user")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable Long userId) {
        System.out.println("Soft deleting user: " + userId);
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
    }

    // ============================================================
    // DELETE - HARD
    // ============================================================

    @DeleteMapping("/{userId}/hard")
    @RequiresRole({"DIRECTOR"})
    @RequiresPermission("USER_DELETE")
    @Operation(summary = "Hard delete user")
    public ResponseEntity<ApiResponse<Void>> hardDeleteUser(
            @PathVariable Long userId) {
        System.out.println("Hard deleting user: " + userId);
        userService.hardDeleteUser(userId);
        return ResponseEntity.ok(ApiResponse.success("User permanently deleted", null));
    }
}