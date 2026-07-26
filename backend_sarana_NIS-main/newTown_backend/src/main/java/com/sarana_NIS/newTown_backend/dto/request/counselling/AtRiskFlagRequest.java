package com.sarana_NIS.newTown_backend.dto.request.counselling;

import com.sarana_NIS.newTown_backend.model.enums.IssueSeverity;
import com.sarana_NIS.newTown_backend.model.enums.IssueType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AtRiskFlagRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Issue type is required")
    private IssueType issueType;

    @NotNull(message = "Severity is required")
    private IssueSeverity severity;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @Size(max = 500, message = "Action plan must be less than 500 characters")
    private String actionPlan;

    private Boolean notifyParents = false;
    private Boolean notifyTeachers = false;

    // Constructors
    public AtRiskFlagRequest() {}

    public AtRiskFlagRequest(Long studentId, IssueType issueType, IssueSeverity severity) {
        this.studentId = studentId;
        this.issueType = issueType;
        this.severity = severity;
    }

    // Getters and Setters
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public IssueType getIssueType() { return issueType; }
    public void setIssueType(IssueType issueType) { this.issueType = issueType; }

    public IssueSeverity getSeverity() { return severity; }
    public void setSeverity(IssueSeverity severity) { this.severity = severity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getActionPlan() { return actionPlan; }
    public void setActionPlan(String actionPlan) { this.actionPlan = actionPlan; }

    public Boolean getNotifyParents() { return notifyParents; }
    public void setNotifyParents(Boolean notifyParents) { this.notifyParents = notifyParents; }

    public Boolean getNotifyTeachers() { return notifyTeachers; }
    public void setNotifyTeachers(Boolean notifyTeachers) { this.notifyTeachers = notifyTeachers; }
}