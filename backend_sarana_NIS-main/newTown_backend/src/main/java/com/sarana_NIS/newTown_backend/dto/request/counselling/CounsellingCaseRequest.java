package com.sarana_NIS.newTown_backend.dto.request.counselling;

import com.sarana_NIS.newTown_backend.model.enums.IssueSeverity;
import com.sarana_NIS.newTown_backend.model.enums.IssueType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CounsellingCaseRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Issue type is required")
    private IssueType issueType;

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 200, message = "Title must be between 2 and 200 characters")
    private String title;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @NotNull(message = "Severity is required")
    private IssueSeverity severity;

    private Long openedBy;

    // Constructors
    public CounsellingCaseRequest() {}

    public CounsellingCaseRequest(Long studentId, IssueType issueType, String title, IssueSeverity severity) {
        this.studentId = studentId;
        this.issueType = issueType;
        this.title = title;
        this.severity = severity;
    }

    // Getters and Setters
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public IssueType getIssueType() { return issueType; }
    public void setIssueType(IssueType issueType) { this.issueType = issueType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public IssueSeverity getSeverity() { return severity; }
    public void setSeverity(IssueSeverity severity) { this.severity = severity; }

    public Long getOpenedBy() { return openedBy; }
    public void setOpenedBy(Long openedBy) { this.openedBy = openedBy; }
}