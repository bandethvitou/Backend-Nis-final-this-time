package com.sarana_NIS.newTown_backend.model.enums;

public enum StudentStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    GRADUATED("Graduated"),
    SUSPENDED("Suspended"),
    EXPULSION("Expulsion"),
    TRANSFERRED("Transferred");

    private final String displayName;

    StudentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}