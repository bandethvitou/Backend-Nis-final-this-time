package com.sarana_NIS.newTown_backend.dto.response.management;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportStatsResponse {

    private Integer totalReports;
    private Integer draft;
    private Integer submitted;
    private Integer underReview;
    private Integer approved;
    private Integer rejected;
    private Integer revisionRequests;
    private Double approvalRate;

    // Constructors
    public ReportStatsResponse() {}

    public ReportStatsResponse(Integer totalReports, Integer approved, Double approvalRate) {
        this.totalReports = totalReports;
        this.approved = approved;
        this.approvalRate = approvalRate;
    }

    // Getters and Setters
    public Integer getTotalReports() { return totalReports; }
    public void setTotalReports(Integer totalReports) { this.totalReports = totalReports; }

    public Integer getDraft() { return draft; }
    public void setDraft(Integer draft) { this.draft = draft; }

    public Integer getSubmitted() { return submitted; }
    public void setSubmitted(Integer submitted) { this.submitted = submitted; }

    public Integer getUnderReview() { return underReview; }
    public void setUnderReview(Integer underReview) { this.underReview = underReview; }

    public Integer getApproved() { return approved; }
    public void setApproved(Integer approved) { this.approved = approved; }

    public Integer getRejected() { return rejected; }
    public void setRejected(Integer rejected) { this.rejected = rejected; }

    public Integer getRevisionRequests() { return revisionRequests; }
    public void setRevisionRequests(Integer revisionRequests) { this.revisionRequests = revisionRequests; }

    public Double getApprovalRate() { return approvalRate; }
    public void setApprovalRate(Double approvalRate) { this.approvalRate = approvalRate; }
}