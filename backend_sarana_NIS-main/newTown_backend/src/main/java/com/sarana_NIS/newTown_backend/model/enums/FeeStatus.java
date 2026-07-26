package com.sarana_NIS.newTown_backend.model.enums;

public enum FeeStatus {
    DUE("Due"),
    PAID("Paid"),
    OVERDUE("Overdue"),
    PARTIALLY_PAID("Partially Paid"),
    WAIVED("Waived"),
    DISPUTED("Disputed");

    private final String displayName;

    FeeStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}