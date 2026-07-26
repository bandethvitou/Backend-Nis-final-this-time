package com.sarana_NIS.newTown_backend.dto.request.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class BulkUserActionRequest {

    @NotEmpty(message = "User IDs list cannot be empty")
    private List<Long> userIds = new ArrayList<>();

    @Pattern(regexp = "^(ACTIVATE|DEACTIVATE|DELETE|ASSIGN_ROLE|REMOVE_ROLE)$",
            message = "Invalid action")
    private String action;

    private Long roleId;

    @Size(max = 500, message = "Reason must be less than 500 characters")
    private String reason;

    // Constructors
    public BulkUserActionRequest() {}

    public BulkUserActionRequest(List<Long> userIds, String action) {
        this.userIds = userIds;
        this.action = action;
    }

    // Getters and Setters
    public List<Long> getUserIds() { return userIds; }
    public void setUserIds(List<Long> userIds) { this.userIds = userIds; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public Long getRoleId() { return roleId; }
    public void setRoleId(Long roleId) { this.roleId = roleId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}