package com.sarana_NIS.newTown_backend.model.enums;

public enum StaffStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    ON_LEAVE("On Leave"),
    TERMINATED("Terminated"),
    SUSPENDED("Suspended");

    private final String displayName;

    StaffStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}