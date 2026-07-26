package com.sarana_NIS.newTown_backend.model.enums;

public enum ReportType {
    ACADEMIC("Academic Report"),
    FINANCIAL("Financial Report"),
    ADMINISTRATIVE("Administrative Report"),
    PERFORMANCE("Performance Report"),
    ATTENDANCE("Attendance Report"),
    DISCIPLINE("Discipline Report"),
    INVENTORY("Inventory Report"),
    STAFF("Staff Report"),
    STUDENT("Student Report"),
    GENERAL("General Report");

    private final String displayName;

    ReportType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}