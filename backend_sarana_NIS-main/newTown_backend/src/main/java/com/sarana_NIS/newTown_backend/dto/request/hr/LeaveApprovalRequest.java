package com.sarana_NIS.newTown_backend.dto.request.hr;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LeaveApprovalRequest {

    @NotNull(message = "Leave ID is required")
    private Long leaveId;

    @NotNull(message = "Action is required")
    private String action;

    @Size(max = 500, message = "Reason must be less than 500 characters")
    private String reason;

    // Constructors
    public LeaveApprovalRequest() {}

    public LeaveApprovalRequest(Long leaveId, String action) {
        this.leaveId = leaveId;
        this.action = action;
    }

    // Getters and Setters
    public Long getLeaveId() { return leaveId; }
    public void setLeaveId(Long leaveId) { this.leaveId = leaveId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}