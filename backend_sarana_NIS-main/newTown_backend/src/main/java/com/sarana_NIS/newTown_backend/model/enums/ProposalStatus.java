package com.sarana_NIS.newTown_backend.model.enums;

public enum ProposalStatus {
    PENDING("Pending"),
    UNDER_REVIEW("Under Review"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    ESCALATED("Escalated"),
    IMPLEMENTED("Implemented"),
    CANCELLED("Cancelled");

    private final String displayName;

    ProposalStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}