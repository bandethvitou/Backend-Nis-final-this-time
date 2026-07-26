package com.sarana_NIS.newTown_backend.model.enums;

public enum IssueType {
    ACADEMIC("Academic"),
    BEHAVIORAL("Behavioral"),
    EMOTIONAL("Emotional"),
    SOCIAL("Social"),
    FAMILY("Family"),
    MENTAL_HEALTH("Mental Health"),
    SUBSTANCE_ABUSE("Substance Abuse"),
    BULLYING("Bullying"),
    GRIEF("Grief"),
    TRANSITION("Transition"),
    OTHER("Other");

    private final String displayName;

    IssueType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}