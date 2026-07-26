package com.sarana_NIS.newTown_backend.model.enums;

public enum ReportStatus {
    DRAFT("Draft"),
    SUBMITTED("Submitted"),
    UNDER_REVIEW("Under Review"),
    REVIEWED("Reviewed"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    PUBLISHED("Published"),
    ARCHIVED("Archived");

    private final String displayName;

    ReportStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}