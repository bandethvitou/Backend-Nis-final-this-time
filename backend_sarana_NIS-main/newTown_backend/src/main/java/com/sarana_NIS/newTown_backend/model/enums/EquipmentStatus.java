package com.sarana_NIS.newTown_backend.model.enums;

public enum EquipmentStatus {
    AVAILABLE("Available"),
    ASSIGNED("Assigned"),
    UNDER_MAINTENANCE("Under Maintenance"),
    UNDER_REPAIR("Under Repair"),
    FUNCTIONAL("Functional"),
    OUT_OF_SERVICE("Out of Service"),
    RESERVED("Reserved"),
    DISPOSED("Disposed"),
    DAMAGED("Damaged"),
    LOST("Lost");

    private final String displayName;

    EquipmentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}