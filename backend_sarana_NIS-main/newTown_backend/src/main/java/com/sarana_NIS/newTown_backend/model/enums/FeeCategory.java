package com.sarana_NIS.newTown_backend.model.enums;

public enum FeeCategory {
    TUITION("Tuition Fee"),
    REGISTRATION("Registration Fee"),
    EXAMINATION("Examination Fee"),
    LABORATORY("Laboratory Fee"),
    LIBRARY("Library Fee"),
    SPORTS("Sports Fee"),
    ACTIVITIES("Activities Fee"),
    TRANSPORTATION("Transportation Fee"),
    UNIFORM("Uniform Fee"),
    BOOKS("Books Fee"),
    INSURANCE("Insurance Fee"),
    FIELD_TRIP("Field Trip Fee"),
    OTHER("Other");

    private final String displayName;

    FeeCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}