package com.sarana_NIS.newTown_backend.dto.response.counselling;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarana_NIS.newTown_backend.model.enums.CaseStatus;
import com.sarana_NIS.newTown_backend.model.enums.IssueSeverity;
import com.sarana_NIS.newTown_backend.model.enums.IssueType;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaseSummaryResponse {

    private Long id;
    private String studentName;
    private String studentId;
    private IssueType issueType;
    private String title;
    private IssueSeverity severity;
    private CaseStatus status;
    private String openedBy;
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;
    private Integer sessionCount;
    private LocalDateTime lastSessionAt;
    private Boolean isAtRisk;
    private String actionPlan;

    // Constructors
    public CaseSummaryResponse() {}

    public CaseSummaryResponse(Long id, String studentName) {
        this.id = id;
        this.studentName = studentName;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public IssueType getIssueType() { return issueType; }
    public void setIssueType(IssueType issueType) { this.issueType = issueType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public IssueSeverity getSeverity() { return severity; }
    public void setSeverity(IssueSeverity severity) { this.severity = severity; }

    public CaseStatus getStatus() { return status; }
    public void setStatus(CaseStatus status) { this.status = status; }

    public String getOpenedBy() { return openedBy; }
    public void setOpenedBy(String openedBy) { this.openedBy = openedBy; }

    public LocalDateTime getOpenedAt() { return openedAt; }
    public void setOpenedAt(LocalDateTime openedAt) { this.openedAt = openedAt; }

    public LocalDateTime getClosedAt() { return closedAt; }
    public void setClosedAt(LocalDateTime closedAt) { this.closedAt = closedAt; }

    public Integer getSessionCount() { return sessionCount; }
    public void setSessionCount(Integer sessionCount) { this.sessionCount = sessionCount; }

    public LocalDateTime getLastSessionAt() { return lastSessionAt; }
    public void setLastSessionAt(LocalDateTime lastSessionAt) { this.lastSessionAt = lastSessionAt; }

    public Boolean getIsAtRisk() { return isAtRisk; }
    public void setIsAtRisk(Boolean isAtRisk) { this.isAtRisk = isAtRisk; }

    public String getActionPlan() { return actionPlan; }
    public void setActionPlan(String actionPlan) { this.actionPlan = actionPlan; }
}