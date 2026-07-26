package com.sarana_NIS.newTown_backend.model.enums;

public enum UserStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    LOCKED("Locked"),
    SUSPENDED("Suspended"),
    DELETED("Deleted"),
    PENDING_VERIFICATION("Pending Verification");

    private final String displayName;

    UserStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}