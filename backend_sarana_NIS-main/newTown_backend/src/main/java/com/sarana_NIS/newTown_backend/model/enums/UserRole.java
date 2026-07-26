package com.sarana_NIS.newTown_backend.model.enums;

public enum UserRole {
    ADMIN("Administrator"),
    MANAGER("Manager"),
    TOP_MANAGER("Top Manager"),
    DIRECTOR("Director"),
    TEACHER("Teacher"),
    STUDENT("Student"),
    PARENT("Parent"),
    FINANCE_ADMIN("Finance Administrator"),
    STAFF("Staff");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}