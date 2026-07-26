package com.sarana_NIS.newTown_backend.dto.response.finance;

import java.math.BigDecimal;

public class SalarySummaryResponse {
    private BigDecimal totalSalaries;
    private BigDecimal paid;
    private BigDecimal pending;
    private Integer totalStaff;
    private Integer paidStaff;
    private Integer pendingStaff;

    // Constructors
    public SalarySummaryResponse() {
        this.totalSalaries = BigDecimal.ZERO;
        this.paid = BigDecimal.ZERO;
        this.pending = BigDecimal.ZERO;
        this.totalStaff = 0;
        this.paidStaff = 0;
        this.pendingStaff = 0;
    }

    // Getters and Setters
    public BigDecimal getTotalSalaries() {
        return totalSalaries;
    }

    public void setTotalSalaries(BigDecimal totalSalaries) {
        this.totalSalaries = totalSalaries;
    }

    public BigDecimal getPaid() {
        return paid;
    }

    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }

    public BigDecimal getPending() {
        return pending;
    }

    public void setPending(BigDecimal pending) {
        this.pending = pending;
    }

    public Integer getTotalStaff() {
        return totalStaff;
    }

    public void setTotalStaff(Integer totalStaff) {
        this.totalStaff = totalStaff;
    }

    public void setTotalStaff(Long totalStaff) {
        this.totalStaff = totalStaff != null ? totalStaff.intValue() : 0;
    }

    public Integer getPaidStaff() {
        return paidStaff;
    }

    public void setPaidStaff(Integer paidStaff) {
        this.paidStaff = paidStaff;
    }

    public void setPaidStaff(Long paidStaff) {
        this.paidStaff = paidStaff != null ? paidStaff.intValue() : 0;
    }

    public Integer getPendingStaff() {
        return pendingStaff;
    }

    public void setPendingStaff(Integer pendingStaff) {
        this.pendingStaff = pendingStaff;
    }

    public void setPendingStaff(Long pendingStaff) {
        this.pendingStaff = pendingStaff != null ? pendingStaff.intValue() : 0;
    }
}