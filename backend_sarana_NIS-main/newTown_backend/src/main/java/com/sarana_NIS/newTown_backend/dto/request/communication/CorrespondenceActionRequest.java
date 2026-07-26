package com.sarana_NIS.newTown_backend.dto.request.communication;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CorrespondenceActionRequest {

    @NotNull(message = "Correspondence ID is required")
    private Long correspondenceId;

    @NotNull(message = "Action is required")
    private String action;

    private String replyContent;
    private String forwardedTo;

    @Size(max = 500, message = "Notes must be less than 500 characters")
    private String notes;

    // Constructors
    public CorrespondenceActionRequest() {}

    public CorrespondenceActionRequest(Long correspondenceId, String action) {
        this.correspondenceId = correspondenceId;
        this.action = action;
    }

    // Getters and Setters
    public Long getCorrespondenceId() { return correspondenceId; }
    public void setCorrespondenceId(Long correspondenceId) { this.correspondenceId = correspondenceId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getReplyContent() { return replyContent; }
    public void setReplyContent(String replyContent) { this.replyContent = replyContent; }

    public String getForwardedTo() { return forwardedTo; }
    public void setForwardedTo(String forwardedTo) { this.forwardedTo = forwardedTo; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}