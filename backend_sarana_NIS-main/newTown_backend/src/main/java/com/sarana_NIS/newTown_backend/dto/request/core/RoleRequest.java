package com.sarana_NIS.newTown_backend.dto.request.core;

import com.sarana_NIS.newTown_backend.model.enums.RoleCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class RoleRequest {

    @NotBlank(message = "Role name is required")
    @Size(max = 50, message = "Role name must be less than 50 characters")
    private String name;

    @Size(max = 100, message = "Display name must be less than 100 characters")
    private String displayName;

    @NotNull(message = "Category is required")
    private RoleCategory category;  // ✅ This is RoleCategory enum

    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    private List<String> permissions = new ArrayList<>();

    @Size(max = 255, message = "Dashboard route must be less than 255 characters")
    private String dashboardRoute;

    // Constructors
    public RoleRequest() {
    }

    public RoleRequest(String name, String displayName, String description) {
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.permissions = new ArrayList<>();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public RoleCategory getCategory() {
        return category;
    }

    public void setCategory(RoleCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public String getDashboardRoute() {
        return dashboardRoute;
    }

    public void setDashboardRoute(String dashboardRoute) {
        this.dashboardRoute = dashboardRoute;
    }
}