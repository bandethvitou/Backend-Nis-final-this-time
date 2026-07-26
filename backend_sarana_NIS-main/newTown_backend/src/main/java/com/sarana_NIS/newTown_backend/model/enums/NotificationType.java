package com.sarana_NIS.newTown_backend.model.enums;

public enum NotificationType {
    INFO("Information"),
    SUCCESS("Success"),
    WARNING("Warning"),
    ERROR("Error"),
    ALERT("Alert"),
    REMINDER("Reminder"),
    TASK("Task"),
    ANNOUNCEMENT("Announcement"),
    SYSTEM("System"),
    EMAIL("Email"),
    SMS("SMS"),
    PUSH("Push Notification"),
    IN_APP("In-App Notification");

    private final String displayName;

    NotificationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}