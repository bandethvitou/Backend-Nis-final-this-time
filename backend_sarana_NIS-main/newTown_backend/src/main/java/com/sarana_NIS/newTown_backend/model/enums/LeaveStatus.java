package com.sarana_NIS.newTown_backend.model.enums;

public enum LeaveStatus {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected"),  // Changed from DENIED to REJECTED
    CANCELLED("Cancelled");

    private final String displayName;

    LeaveStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}