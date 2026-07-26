package com.sarana_NIS.newTown_backend.dto.request.management;

import com.sarana_NIS.newTown_backend.model.enums.ReportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ReportRequest {

    @NotNull(message = "Report type is required")
    private ReportType type;

    @NotBlank(message = "Report title is required")
    private String title;

    private String content;
    private Long departmentId;
    private Long submittedToUserId;
    private Long approvedByUserId;
    private String priority;
    private LocalDate reportPeriodStart;
    private LocalDate reportPeriodEnd;

    private Map<String, Object> data = new HashMap<>();
    private Map<String, String> metrics = new HashMap<>();

    private String executiveSummary;
    private String recommendations;
    private String nextSteps;

    // Constructors
    public ReportRequest() {}

    public ReportRequest(ReportType type, String title) {
        this.type = type;
        this.title = title;
    }

    // Getters and Setters
    public ReportType getType() { return type; }
    public void setType(ReportType type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }

    public Long getSubmittedToUserId() { return submittedToUserId; }
    public void setSubmittedToUserId(Long submittedToUserId) { this.submittedToUserId = submittedToUserId; }

    public Long getApprovedByUserId() { return approvedByUserId; }
    public void setApprovedByUserId(Long approvedByUserId) { this.approvedByUserId = approvedByUserId; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public LocalDate getReportPeriodStart() { return reportPeriodStart; }
    public void setReportPeriodStart(LocalDate reportPeriodStart) { this.reportPeriodStart = reportPeriodStart; }

    public LocalDate getReportPeriodEnd() { return reportPeriodEnd; }
    public void setReportPeriodEnd(LocalDate reportPeriodEnd) { this.reportPeriodEnd = reportPeriodEnd; }

    public Map<String, Object> getData() { return data; }
    public void setData(Map<String, Object> data) { this.data = data; }

    public Map<String, String> getMetrics() { return metrics; }
    public void setMetrics(Map<String, String> metrics) { this.metrics = metrics; }

    public String getExecutiveSummary() { return executiveSummary; }
    public void setExecutiveSummary(String executiveSummary) { this.executiveSummary = executiveSummary; }

    public String getRecommendations() { return recommendations; }
    public void setRecommendations(String recommendations) { this.recommendations = recommendations; }

    public String getNextSteps() { return nextSteps; }
    public void setNextSteps(String nextSteps) { this.nextSteps = nextSteps; }
}