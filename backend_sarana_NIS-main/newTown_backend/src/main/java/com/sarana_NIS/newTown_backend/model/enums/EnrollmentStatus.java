package com.sarana_NIS.newTown_backend.model.enums;

public enum EnrollmentStatus {
    ACTIVE("Active"),
    ENROLLED("Enrolled"),
    GRADUATED("Graduated"),
    DROPPED("Dropped"),
    SUSPENDED("Suspended"),
    INACTIVE("Inactive"),
    TRANSFERRED("Transferred"),
    WITHDRAWN("Withdrawn");

    private final String displayName;

    EnrollmentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}