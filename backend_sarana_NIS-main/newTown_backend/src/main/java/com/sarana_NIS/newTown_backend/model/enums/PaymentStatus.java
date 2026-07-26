package com.sarana_NIS.newTown_backend.model.enums;

public enum PaymentStatus {
    PENDING("Pending"),
    PAID("Paid"),
    REJECTED("Rejected"),
    OVERDUE("Overdue"),
    CANCELLED("Cancelled");

    private final String displayName;

    PaymentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}