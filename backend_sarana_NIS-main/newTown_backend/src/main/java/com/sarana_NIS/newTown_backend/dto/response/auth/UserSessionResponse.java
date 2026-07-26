package com.sarana_NIS.newTown_backend.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSessionResponse {

    private Long id;
    private String username;
    private String fullName;
    private String email;
    private List<SessionInfo> activeSessions = new ArrayList<>();
    private Integer totalSessions;

    // Constructors
    public UserSessionResponse() {}

    public UserSessionResponse(Long id, String username, String fullName) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<SessionInfo> getActiveSessions() { return activeSessions; }
    public void setActiveSessions(List<SessionInfo> activeSessions) { this.activeSessions = activeSessions; }

    public Integer getTotalSessions() { return totalSessions; }
    public void setTotalSessions(Integer totalSessions) { this.totalSessions = totalSessions; }

    // Inner class
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SessionInfo {
        private String sessionId;
        private String deviceInfo;
        private String ipAddress;
        private LocalDateTime loginTime;
        private LocalDateTime lastActivity;
        private Boolean isCurrentSession;

        // Constructors
        public SessionInfo() {}

        public SessionInfo(String sessionId, String deviceInfo, LocalDateTime loginTime) {
            this.sessionId = sessionId;
            this.deviceInfo = deviceInfo;
            this.loginTime = loginTime;
        }

        // Getters and Setters
        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }

        public String getDeviceInfo() { return deviceInfo; }
        public void setDeviceInfo(String deviceInfo) { this.deviceInfo = deviceInfo; }

        public String getIpAddress() { return ipAddress; }
        public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

        public LocalDateTime getLoginTime() { return loginTime; }
        public void setLoginTime(LocalDateTime loginTime) { this.loginTime = loginTime; }

        public LocalDateTime getLastActivity() { return lastActivity; }
        public void setLastActivity(LocalDateTime lastActivity) { this.lastActivity = lastActivity; }

        public Boolean getIsCurrentSession() { return isCurrentSession; }
        public void setIsCurrentSession(Boolean isCurrentSession) { this.isCurrentSession = isCurrentSession; }
    }
}