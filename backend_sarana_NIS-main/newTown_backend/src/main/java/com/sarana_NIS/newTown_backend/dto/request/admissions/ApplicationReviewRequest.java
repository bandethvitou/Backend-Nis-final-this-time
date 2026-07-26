package com.sarana_NIS.newTown_backend.dto.request.admissions;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ApplicationReviewRequest {

    @NotNull(message = "Application ID is required")
    private Long applicationId;

    @NotNull(message = "Action is required")
    private String action;

    @Size(max = 500, message = "Review notes must be less than 500 characters")
    private String reviewNotes;

    private Long assignedClassId;
    private String rejectionReason;
    private String documentsRequested;

    // Constructors
    public ApplicationReviewRequest() {}

    public ApplicationReviewRequest(Long applicationId, String action) {
        this.applicationId = applicationId;
        this.action = action;
    }

    // Getters and Setters
    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getReviewNotes() { return reviewNotes; }
    public void setReviewNotes(String reviewNotes) { this.reviewNotes = reviewNotes; }

    public Long getAssignedClassId() { return assignedClassId; }
    public void setAssignedClassId(Long assignedClassId) { this.assignedClassId = assignedClassId; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public String getDocumentsRequested() { return documentsRequested; }
    public void setDocumentsRequested(String documentsRequested) { this.documentsRequested = documentsRequested; }
}