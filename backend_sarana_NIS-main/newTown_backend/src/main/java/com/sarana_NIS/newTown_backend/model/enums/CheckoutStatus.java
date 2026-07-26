package com.sarana_NIS.newTown_backend.model.enums;

public enum CheckoutStatus {
    CHECKED_OUT("Checked Out"),
    RETURNED("Returned"),
    OVERDUE("Overdue"),
    RESERVED("Reserved"),
    LOST("Lost"),
    DAMAGED("Damaged");

    private final String displayName;

    CheckoutStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}