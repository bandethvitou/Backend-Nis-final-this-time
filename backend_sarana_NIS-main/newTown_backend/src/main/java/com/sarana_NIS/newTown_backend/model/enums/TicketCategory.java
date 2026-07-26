package com.sarana_NIS.newTown_backend.model.enums;

public enum TicketCategory {
    HARDWARE("Hardware"),
    SOFTWARE("Software"),
    NETWORK("Network"),
    DATABASE("Database"),
    SECURITY("Security"),
    PRINTER("Printer"),
    EMAIL("Email"),
    ACCOUNT("Account"),
    ACCESS("Access"),
    OTHER("Other"),
    SYSTEM("System"),
    APPLICATION("Application"),
    WEBSITE("Website"),
    MOBILE("Mobile"),
    TRAINING("Training");

    private final String displayName;

    TicketCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}