package com.sarana_NIS.newTown_backend.dto.response.core;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleWithUsersResponse {

    private RoleResponse role;
    private List<UserSummary> users = new ArrayList<>();
    private Integer totalUsers;
    private Integer activeUsers;
    private Integer inactiveUsers;

    // Constructors
    public RoleWithUsersResponse() {}

    // Getters and Setters
    public RoleResponse getRole() { return role; }
    public void setRole(RoleResponse role) { this.role = role; }

    public List<UserSummary> getUsers() { return users; }
    public void setUsers(List<UserSummary> users) { this.users = users; }

    public Integer getTotalUsers() { return totalUsers; }
    public void setTotalUsers(Integer totalUsers) { this.totalUsers = totalUsers; }

    public Integer getActiveUsers() { return activeUsers; }
    public void setActiveUsers(Integer activeUsers) { this.activeUsers = activeUsers; }

    public Integer getInactiveUsers() { return inactiveUsers; }
    public void setInactiveUsers(Integer inactiveUsers) { this.inactiveUsers = inactiveUsers; }

    // Inner class
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UserSummary {
        private Long id;
        private String username;
        private String fullName;
        private String email;
        private String status;
        private LocalDateTime lastLogin;
        private String avatarUrl;

        // Constructors
        public UserSummary() {}

        public UserSummary(Long id, String username, String fullName) {
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

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public LocalDateTime getLastLogin() { return lastLogin; }
        public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

        public String getAvatarUrl() { return avatarUrl; }
        public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    }
}