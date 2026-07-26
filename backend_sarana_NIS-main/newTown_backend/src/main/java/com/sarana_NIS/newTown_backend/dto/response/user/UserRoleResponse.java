package com.sarana_NIS.newTown_backend.dto.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRoleResponse {

    private Long userId;
    private String username;
    private String fullName;
    private List<RoleDetail> roles = new ArrayList<>();
    private String primaryRole;
    private Integer totalRoles;
    private Boolean isMultiRole;

    // Constructors
    public UserRoleResponse() {}

    public UserRoleResponse(Long userId, String username, String fullName) {
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

    public List<RoleDetail> getRoles() { return roles; }
    public void setRoles(List<RoleDetail> roles) { this.roles = roles; }

    public String getPrimaryRole() { return primaryRole; }
    public void setPrimaryRole(String primaryRole) { this.primaryRole = primaryRole; }

    public Integer getTotalRoles() { return totalRoles; }
    public void setTotalRoles(Integer totalRoles) { this.totalRoles = totalRoles; }

    public Boolean getIsMultiRole() { return isMultiRole; }
    public void setIsMultiRole(Boolean isMultiRole) { this.isMultiRole = isMultiRole; }

    // Inner class
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RoleDetail {
        private Long id;
        private String name;
        private String displayName;
        private String category;
        private List<String> permissions = new ArrayList<>();
        private String dashboardRoute;

        // Constructors
        public RoleDetail() {}

        public RoleDetail(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getDisplayName() { return displayName; }
        public void setDisplayName(String displayName) { this.displayName = displayName; }

        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }

        public List<String> getPermissions() { return permissions; }
        public void setPermissions(List<String> permissions) { this.permissions = permissions; }

        public String getDashboardRoute() { return dashboardRoute; }
        public void setDashboardRoute(String dashboardRoute) { this.dashboardRoute = dashboardRoute; }
    }
}