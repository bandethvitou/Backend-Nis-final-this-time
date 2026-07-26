package com.sarana_NIS.newTown_backend.model.enums;

public enum Shift {
    MORNING("Morning Shift"),
    AFTERNOON("Afternoon Shift"),
    EVENING("Evening Shift"),
    NIGHT("Night Shift"),
    FULL_DAY("Full Day"),
    ROTATING("Rotating Shift"),
    FLEXIBLE("Flexible Shift");

    private final String displayName;

    Shift(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}