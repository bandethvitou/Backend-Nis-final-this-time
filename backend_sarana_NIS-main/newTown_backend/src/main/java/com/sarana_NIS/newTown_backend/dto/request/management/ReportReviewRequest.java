package com.sarana_NIS.newTown_backend.dto.request.management;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReportReviewRequest {

    @NotNull(message = "Report ID is required")
    private Long reportId;

    @NotNull(message = "Action is required")
    private String action;

    @Size(max = 500, message = "Feedback must be less than 500 characters")
    private String feedback;

    private String revisionNotes;
    private String rejectionReason;

    // Constructors
    public ReportReviewRequest() {}

    public ReportReviewRequest(Long reportId, String action) {
        this.reportId = reportId;
        this.action = action;
    }

    // Getters and Setters
    public Long getReportId() { return reportId; }
    public void setReportId(Long reportId) { this.reportId = reportId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

    public String getRevisionNotes() { return revisionNotes; }
    public void setRevisionNotes(String revisionNotes) { this.revisionNotes = revisionNotes; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
}