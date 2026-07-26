package com.sarana_NIS.newTown_backend.dto.request.hr;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class HiringApprovalRequest {

    @NotNull(message = "Hiring ID is required")
    private Long hiringId;

    @NotNull(message = "Action is required")
    private String action;

    @Size(max = 500, message = "Reason must be less than 500 characters")
    private String reason;

    private Integer approvedPositions;

    // Constructors
    public HiringApprovalRequest() {}

    public HiringApprovalRequest(Long hiringId, String action) {
        this.hiringId = hiringId;
        this.action = action;
    }

    // Getters and Setters
    public Long getHiringId() { return hiringId; }
    public void setHiringId(Long hiringId) { this.hiringId = hiringId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Integer getApprovedPositions() { return approvedPositions; }
    public void setApprovedPositions(Integer approvedPositions) { this.approvedPositions = approvedPositions; }
}