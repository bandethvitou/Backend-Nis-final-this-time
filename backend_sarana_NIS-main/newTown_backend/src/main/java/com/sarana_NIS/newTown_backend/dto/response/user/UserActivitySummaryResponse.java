package com.sarana_NIS.newTown_backend.dto.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserActivitySummaryResponse {

    private Long userId;
    private String username;
    private String fullName;
    private LocalDateTime lastLogin;
    private Integer totalLogins;
    private Integer failedLoginAttempts;
    private Map<String, Integer> loginByMonth = new HashMap<>();
    private Boolean isLocked;
    private Boolean isActive;
    private Integer sessionCount;
    private LocalDateTime lastActivity;

    // Constructors
    public UserActivitySummaryResponse() {}

    public UserActivitySummaryResponse(Long userId, String username, String fullName) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
    }

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    public Integer getTotalLogins() { return totalLogins; }
    public void setTotalLogins(Integer totalLogins) { this.totalLogins = totalLogins; }

    public Integer getFailedLoginAttempts() { return failedLoginAttempts; }
    public void setFailedLoginAttempts(Integer failedLoginAttempts) { this.failedLoginAttempts = failedLoginAttempts; }

    public Map<String, Integer> getLoginByMonth() { return loginByMonth; }
    public void setLoginByMonth(Map<String, Integer> loginByMonth) { this.loginByMonth = loginByMonth; }

    public Boolean getIsLocked() { return isLocked; }
    public void setIsLocked(Boolean isLocked) { this.isLocked = isLocked; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Integer getSessionCount() { return sessionCount; }
    public void setSessionCount(Integer sessionCount) { this.sessionCount = sessionCount; }

    public LocalDateTime getLastActivity() { return lastActivity; }
    public void setLastActivity(LocalDateTime lastActivity) { this.lastActivity = lastActivity; }
}