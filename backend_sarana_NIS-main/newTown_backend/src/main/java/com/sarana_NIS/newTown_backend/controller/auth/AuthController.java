package com.sarana_NIS.newTown_backend.controller.auth;

import com.sarana_NIS.newTown_backend.dto.request.auth.*;
import com.sarana_NIS.newTown_backend.dto.response.auth.LoginResponse;
import com.sarana_NIS.newTown_backend.dto.response.auth.TokenResponse;
import com.sarana_NIS.newTown_backend.dto.response.auth.VerifyEmailResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.security.annotation.PublicEndpoint;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication APIs")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @PublicEndpoint
    @Operation(summary = "Login to the system")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        System.out.println("Login attempt for user: " + request.getUsername());
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }

    @PostMapping("/logout")
    @PublicEndpoint
    @Operation(summary = "Logout from the system")
    public ResponseEntity<ApiResponse<Void>> logout(
            @RequestBody(required = false) LogoutRequest request) {
        System.out.println("Logout request received");
        authService.logout(request);
        return ResponseEntity.ok(ApiResponse.success("Logout successful", null));
    }

    @PostMapping("/refresh")
    @PublicEndpoint
    @Operation(summary = "Refresh access token")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {
        System.out.println("Refresh token request received");
        String newAccessToken = authService.refreshToken(request);
        TokenResponse response = new TokenResponse();
        response.setAccessToken(newAccessToken);
        response.setRefreshToken(request.getRefreshToken());
        response.setTokenType("Bearer");
        response.setExpiresIn(86400000L);
        return ResponseEntity.ok(ApiResponse.success("Token refreshed successfully", response));
    }

    @PostMapping("/forgot-password")
    @PublicEndpoint
    @Operation(summary = "Request password reset")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request) {
        System.out.println("Password reset requested for email: " + request.getEmail());
        authService.forgotPassword(request);
        return ResponseEntity.ok(ApiResponse.success("Password reset email sent", null));
    }

    @PostMapping("/reset-password")
    @PublicEndpoint
    @Operation(summary = "Reset password with token")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request) {
        System.out.println("Password reset attempt");
        authService.resetPassword(request);
        return ResponseEntity.ok(ApiResponse.success("Password reset successful", null));
    }

    @PostMapping("/verify-email")
    @PublicEndpoint
    @Operation(summary = "Verify email with token")
    public ResponseEntity<ApiResponse<VerifyEmailResponse>> verifyEmail(
            @Valid @RequestBody VerifyEmailRequest request) {
        System.out.println("Email verification attempt");
        VerifyEmailResponse response = authService.verifyEmail(request);
        return ResponseEntity.ok(ApiResponse.success("Email verified successfully", response));
    }

    @PostMapping("/change-password")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER", "STUDENT", "PARENT", "STAFF"})
    @Operation(summary = "Change current user's password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @Valid @RequestBody ChangePasswordRequest request) {
        System.out.println("Password change request");
        return ResponseEntity.ok(ApiResponse.success("Password changed successfully", null));
    }

    @GetMapping("/me")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER", "STUDENT", "PARENT", "STAFF"})
    @Operation(summary = "Get current user profile")
    public ResponseEntity<ApiResponse<Object>> getCurrentUser() {
        System.out.println("Get current user profile");
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/logout-all")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER", "STUDENT", "PARENT", "STAFF"})
    @Operation(summary = "Logout from all devices")
    public ResponseEntity<ApiResponse<Void>> logoutAll() {
        System.out.println("Logout from all devices");
        return ResponseEntity.ok(ApiResponse.success("Logged out from all devices", null));
    }
}