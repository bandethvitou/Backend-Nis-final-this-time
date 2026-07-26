package com.sarana_NIS.newTown_backend.model.enums;

public enum RoleCategory {
    ADMINISTRATIVE("Administrative"),
    ACADEMIC("Academic"),
    FINANCE("Finance"),
    MANAGEMENT("Management"),
    IT("IT"),
    SUPPORT("Support");

    private final String displayName;

    RoleCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}