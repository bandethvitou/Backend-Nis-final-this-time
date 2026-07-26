package com.sarana_NIS.newTown_backend.dto.request.library;

import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class OverdueReminderRequest {

    private List<Long> checkoutIds = new ArrayList<>();
    private Boolean sendToAll = false;

    @Size(max = 500, message = "Message must be less than 500 characters")
    private String customMessage;

    private String reminderType;

    // Constructors
    public OverdueReminderRequest() {}

    public OverdueReminderRequest(List<Long> checkoutIds) {
        this.checkoutIds = checkoutIds;
    }

    // Getters and Setters
    public List<Long> getCheckoutIds() { return checkoutIds; }
    public void setCheckoutIds(List<Long> checkoutIds) { this.checkoutIds = checkoutIds; }

    public Boolean getSendToAll() { return sendToAll; }
    public void setSendToAll(Boolean sendToAll) { this.sendToAll = sendToAll; }

    public String getCustomMessage() { return customMessage; }
    public void setCustomMessage(String customMessage) { this.customMessage = customMessage; }

    public String getReminderType() { return reminderType; }
    public void setReminderType(String reminderType) { this.reminderType = reminderType; }
}