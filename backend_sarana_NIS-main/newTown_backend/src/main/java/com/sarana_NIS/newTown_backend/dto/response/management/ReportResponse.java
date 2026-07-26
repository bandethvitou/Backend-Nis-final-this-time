package com.sarana_NIS.newTown_backend.dto.response.management;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarana_NIS.newTown_backend.model.enums.ReportStatus;
import com.sarana_NIS.newTown_backend.model.enums.ReportType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportResponse {

    // ============================================================
    // BASIC INFORMATION
    // ============================================================
    private Long id;
    private ReportType type;
    private String title;
    private String content;
    private ReportStatus status;

    // ============================================================
    // DEPARTMENT
    // ============================================================
    private String departmentName;

    // ============================================================
    // SUBMITTED BY
    // ============================================================
    private String submittedBy;
    private String submittedByFullName;

    // ============================================================
    // SUBMITTED TO
    // ============================================================
    private String submittedTo;
    private String submittedToFullName;

    // ============================================================
    // REVIEWED BY
    // ============================================================
    private String reviewedBy;
    private String reviewedByName;

    // ============================================================
    // DATES
    // ============================================================
    private LocalDateTime submittedDate;
    private LocalDateTime reviewedDate;

    // ============================================================
    // APPROVAL (DTO-ONLY FIELDS - NOT IN ENTITY)
    // ============================================================
    private String approvedBy;
    private String approvedByFullName;
    private LocalDateTime approvedDate;
    private String priority;
    private LocalDate reportPeriodStart;
    private LocalDate reportPeriodEnd;

    // ============================================================
    // FEEDBACK & REVISION
    // ============================================================
    private String feedback;
    private Boolean revisionRequired;
    private String revisionComment;

    // ============================================================
    // ATTACHMENTS & TAGS
    // ============================================================
    private List<String> attachments = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    // ============================================================
    // ADDITIONAL DTO-ONLY FIELDS
    // ============================================================
    private Object data;
    private Object metrics;
    private String executiveSummary;
    private String recommendations;
    private String nextSteps;

    // ============================================================
    // VIEW & CONFIDENTIALITY
    // ============================================================
    private Boolean isConfidential;
    private Integer viewCount;
    private LocalDateTime lastViewedAt;

    // ============================================================
    // CONSTRUCTORS
    // ============================================================
    public ReportResponse() {}

    public ReportResponse(Long id, String title, ReportStatus status) {
        this.id = id;
        this.title = title;
        this.status = status;
    }

    // ============================================================
    // GETTERS AND SETTERS
    // ============================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public String getSubmittedByFullName() {
        return submittedByFullName;
    }

    public void setSubmittedByFullName(String submittedByFullName) {
        this.submittedByFullName = submittedByFullName;
    }

    public String getSubmittedTo() {
        return submittedTo;
    }

    public void setSubmittedTo(String submittedTo) {
        this.submittedTo = submittedTo;
    }

    public String getSubmittedToFullName() {
        return submittedToFullName;
    }

    public void setSubmittedToFullName(String submittedToFullName) {
        this.submittedToFullName = submittedToFullName;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getReviewedByName() {
        return reviewedByName;
    }

    public void setReviewedByName(String reviewedByName) {
        this.reviewedByName = reviewedByName;
    }

    public LocalDateTime getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDateTime submittedDate) {
        this.submittedDate = submittedDate;
    }

    public LocalDateTime getReviewedDate() {
        return reviewedDate;
    }

    public void setReviewedDate(LocalDateTime reviewedDate) {
        this.reviewedDate = reviewedDate;
    }

    // ============================================================
    // APPROVAL DTO-ONLY GETTERS/SETTERS
    // ============================================================

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getApprovedByFullName() {
        return approvedByFullName;
    }

    public void setApprovedByFullName(String approvedByFullName) {
        this.approvedByFullName = approvedByFullName;
    }

    public LocalDateTime getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(LocalDateTime approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getReportPeriodStart() {
        return reportPeriodStart;
    }

    public void setReportPeriodStart(LocalDate reportPeriodStart) {
        this.reportPeriodStart = reportPeriodStart;
    }

    public LocalDate getReportPeriodEnd() {
        return reportPeriodEnd;
    }

    public void setReportPeriodEnd(LocalDate reportPeriodEnd) {
        this.reportPeriodEnd = reportPeriodEnd;
    }

    // ============================================================
    // FEEDBACK & REVISION GETTERS/SETTERS
    // ============================================================

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Boolean getRevisionRequired() {
        return revisionRequired;
    }

    public void setRevisionRequired(Boolean revisionRequired) {
        this.revisionRequired = revisionRequired;
    }

    public String getRevisionComment() {
        return revisionComment;
    }

    public void setRevisionComment(String revisionComment) {
        this.revisionComment = revisionComment;
    }

    // ============================================================
    // ATTACHMENTS & TAGS GETTERS/SETTERS
    // ============================================================

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    // ============================================================
    // ADDITIONAL DTO-ONLY GETTERS/SETTERS
    // ============================================================

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getMetrics() {
        return metrics;
    }

    public void setMetrics(Object metrics) {
        this.metrics = metrics;
    }

    public String getExecutiveSummary() {
        return executiveSummary;
    }

    public void setExecutiveSummary(String executiveSummary) {
        this.executiveSummary = executiveSummary;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getNextSteps() {
        return nextSteps;
    }

    public void setNextSteps(String nextSteps) {
        this.nextSteps = nextSteps;
    }

    // ============================================================
    // VIEW & CONFIDENTIALITY GETTERS/SETTERS
    // ============================================================

    public Boolean getIsConfidential() {
        return isConfidential;
    }

    public void setIsConfidential(Boolean isConfidential) {
        this.isConfidential = isConfidential;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public LocalDateTime getLastViewedAt() {
        return lastViewedAt;
    }

    public void setLastViewedAt(LocalDateTime lastViewedAt) {
        this.lastViewedAt = lastViewedAt;
    }
}