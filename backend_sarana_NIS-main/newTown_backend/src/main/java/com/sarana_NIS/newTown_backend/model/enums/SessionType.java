package com.sarana_NIS.newTown_backend.model.enums;

public enum SessionType {
    INITIAL_ASSESSMENT("Initial Assessment"),
    INDIVIDUAL("Individual"),
    GROUP("Group"),
    FAMILY("Family"),
    FOLLOW_UP("Follow Up"),
    CRISIS("Crisis"),
    REFERRAL("Referral");

    private final String displayName;

    SessionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}