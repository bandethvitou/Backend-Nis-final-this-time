package com.sarana_NIS.newTown_backend.model.enums;

public enum MaintenanceType {
    PREVENTIVE("Preventive Maintenance"),
    CORRECTIVE("Corrective Maintenance"),
    EMERGENCY("Emergency Maintenance"),
    PREDICTIVE("Predictive Maintenance"),
    ROUTINE("Routine Maintenance"),
    OVERHAUL("Overhaul"),
    REPAIR("Repair"),
    REPLACEMENT("Replacement");

    private final String displayName;

    MaintenanceType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}