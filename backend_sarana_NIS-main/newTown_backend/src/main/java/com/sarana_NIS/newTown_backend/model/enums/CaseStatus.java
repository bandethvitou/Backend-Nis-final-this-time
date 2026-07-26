package com.sarana_NIS.newTown_backend.model.enums;

public enum CaseStatus {
    ACTIVE("Active"),
    IN_PROGRESS("In Progress"),
    RESOLVED("Resolved"),
    CLOSED("Closed"),
    REFERRED("Referred");

    private final String displayName;

    CaseStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}