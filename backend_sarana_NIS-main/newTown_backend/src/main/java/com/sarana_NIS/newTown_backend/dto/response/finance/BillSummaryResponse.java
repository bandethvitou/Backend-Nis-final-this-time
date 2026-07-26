package com.sarana_NIS.newTown_backend.dto.response.finance;

import java.util.Map;

public class BillSummaryResponse {
    private Double totalBills;
    private Double pending;
    private Double approved;
    private Double rejected;
    private Long totalCount;
    private Long pendingCount;
    private Long approvedCount;
    private Long rejectedCount;
    private Map<String, Double> breakdownByType;

    // Constructors
    public BillSummaryResponse() {
        this.totalBills = 0.0;
        this.pending = 0.0;
        this.approved = 0.0;
        this.rejected = 0.0;
        this.totalCount = 0L;
        this.pendingCount = 0L;
        this.approvedCount = 0L;
        this.rejectedCount = 0L;
    }

    // Getters and Setters
    public Double getTotalBills() {
        return totalBills;
    }

    public void setTotalBills(Double totalBills) {
        this.totalBills = totalBills;
    }

    public Double getPending() {
        return pending;
    }

    public void setPending(Double pending) {
        this.pending = pending;
    }

    public Double getApproved() {
        return approved;
    }

    public void setApproved(Double approved) {
        this.approved = approved;
    }

    public Double getRejected() {
        return rejected;
    }

    public void setRejected(Double rejected) {
        this.rejected = rejected;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getPendingCount() {
        return pendingCount;
    }

    public void setPendingCount(Long pendingCount) {
        this.pendingCount = pendingCount;
    }

    public Long getApprovedCount() {
        return approvedCount;
    }

    public void setApprovedCount(Long approvedCount) {
        this.approvedCount = approvedCount;
    }

    public Long getRejectedCount() {
        return rejectedCount;
    }

    public void setRejectedCount(Long rejectedCount) {
        this.rejectedCount = rejectedCount;
    }

    public Map<String, Double> getBreakdownByType() {
        return breakdownByType;
    }

    public void setBreakdownByType(Map<String, Double> breakdownByType) {
        this.breakdownByType = breakdownByType;
    }
}