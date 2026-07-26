package com.sarana_NIS.newTown_backend.dto.response.admissions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationStatsResponse {

    private Integer totalApplications;
    private Integer pendingApplications;
    private Integer approvedApplications;
    private Integer rejectedApplications;
    private Integer enrolledApplications;

    private Map<String, Integer> statusBreakdown = new HashMap<>();
    private Map<String, Integer> levelBreakdown = new HashMap<>();

    private Double conversionRate;
    private Double approvalRate;

    // Constructors
    public ApplicationStatsResponse() {}

    public ApplicationStatsResponse(Integer totalApplications, Integer enrolledApplications) {
        this.totalApplications = totalApplications;
        this.enrolledApplications = enrolledApplications;
    }

    // Getters and Setters
    public Integer getTotalApplications() { return totalApplications; }
    public void setTotalApplications(Integer totalApplications) { this.totalApplications = totalApplications; }

    public Integer getPendingApplications() { return pendingApplications; }
    public void setPendingApplications(Integer pendingApplications) { this.pendingApplications = pendingApplications; }

    public Integer getApprovedApplications() { return approvedApplications; }
    public void setApprovedApplications(Integer approvedApplications) { this.approvedApplications = approvedApplications; }

    public Integer getRejectedApplications() { return rejectedApplications; }
    public void setRejectedApplications(Integer rejectedApplications) { this.rejectedApplications = rejectedApplications; }

    public Integer getEnrolledApplications() { return enrolledApplications; }
    public void setEnrolledApplications(Integer enrolledApplications) { this.enrolledApplications = enrolledApplications; }

    public Map<String, Integer> getStatusBreakdown() { return statusBreakdown; }
    public void setStatusBreakdown(Map<String, Integer> statusBreakdown) { this.statusBreakdown = statusBreakdown; }

    public Map<String, Integer> getLevelBreakdown() { return levelBreakdown; }
    public void setLevelBreakdown(Map<String, Integer> levelBreakdown) { this.levelBreakdown = levelBreakdown; }

    public Double getConversionRate() { return conversionRate; }
    public void setConversionRate(Double conversionRate) { this.conversionRate = conversionRate; }

    public Double getApprovalRate() { return approvalRate; }
    public void setApprovalRate(Double approvalRate) { this.approvalRate = approvalRate; }
}