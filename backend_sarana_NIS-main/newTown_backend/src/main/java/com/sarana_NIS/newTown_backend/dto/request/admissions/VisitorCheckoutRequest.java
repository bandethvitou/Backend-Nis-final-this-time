package com.sarana_NIS.newTown_backend.dto.request.admissions;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class VisitorCheckoutRequest {

    @NotNull(message = "Visitor ID is required")
    private Long visitorId;

    private LocalDateTime checkoutTime;

    @Size(max = 500, message = "Notes must be less than 500 characters")
    private String notes;

    // Constructors
    public VisitorCheckoutRequest() {}

    public VisitorCheckoutRequest(Long visitorId) {
        this.visitorId = visitorId;
    }

    // Getters and Setters
    public Long getVisitorId() { return visitorId; }
    public void setVisitorId(Long visitorId) { this.visitorId = visitorId; }

    public LocalDateTime getCheckoutTime() { return checkoutTime; }
    public void setCheckoutTime(LocalDateTime checkoutTime) { this.checkoutTime = checkoutTime; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}