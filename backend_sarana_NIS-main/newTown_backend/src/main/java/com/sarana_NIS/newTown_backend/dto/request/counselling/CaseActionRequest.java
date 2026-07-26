package com.sarana_NIS.newTown_backend.dto.request.counselling;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CaseActionRequest {

    @NotNull(message = "Case ID is required")
    private Long caseId;

    @NotNull(message = "Action is required")
    private String action;

    @Size(max = 500, message = "Notes must be less than 500 characters")
    private String notes;

    private String resolutionNotes;
    private String escalationReason;

    // Constructors
    public CaseActionRequest() {}

    public CaseActionRequest(Long caseId, String action) {
        this.caseId = caseId;
        this.action = action;
    }

    // Getters and Setters
    public Long getCaseId() { return caseId; }
    public void setCaseId(Long caseId) { this.caseId = caseId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getResolutionNotes() { return resolutionNotes; }
    public void setResolutionNotes(String resolutionNotes) { this.resolutionNotes = resolutionNotes; }

    public String getEscalationReason() { return escalationReason; }
    public void setEscalationReason(String escalationReason) { this.escalationReason = escalationReason; }
}