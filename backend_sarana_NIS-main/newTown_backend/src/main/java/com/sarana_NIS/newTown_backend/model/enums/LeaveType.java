package com.sarana_NIS.newTown_backend.model.enums;

public enum LeaveType {
    ANNUAL("Annual Leave"),
    SICK("Sick Leave"),
    MATERNITY("Maternity Leave"),
    PATERNITY("Paternity Leave"),
    BEREAVEMENT("Bereavement Leave"),
    UNPAID("Unpaid Leave"),
    STUDY("Study Leave"),
    OTHER("Other");

    private final String displayName;

    LeaveType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}