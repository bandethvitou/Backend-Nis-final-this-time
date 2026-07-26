package com.sarana_NIS.newTown_backend.dto.request.admissions;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ApplicationStatusRequest {

    @NotNull(message = "Application ID is required")
    private Long applicationId;

    @NotNull(message = "Status is required")
    @Pattern(regexp = "^(INQUIRY|APPLICATION|REVIEW|CAMPUS_TOUR|ASSESSMENT|APPROVAL|INVOICE|ENROLLMENT)$",
            message = "Invalid status")
    private String status;

    private String notes;

    // Constructors
    public ApplicationStatusRequest() {}

    public ApplicationStatusRequest(Long applicationId, String status) {
        this.applicationId = applicationId;
        this.status = status;
    }

    // Getters and Setters
    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}