package com.sarana_NIS.newTown_backend.dto.response.finance;

import com.sarana_NIS.newTown_backend.model.enums.ExpenseCategory;

import java.util.Map;

public class ExpenseSummaryResponse {
    private Double totalExpenses;
    private Double pending;
    private Double paid;
    private Double rejected;
    private Map<ExpenseCategory, Double> categoryBreakdown;
    private Long totalCount;
    private Long pendingCount;
    private Long paidCount;
    private Long rejectedCount;

    // Constructors
    public ExpenseSummaryResponse() {
        this.totalExpenses = 0.0;
        this.pending = 0.0;
        this.paid = 0.0;
        this.rejected = 0.0;
        this.totalCount = 0L;
        this.pendingCount = 0L;
        this.paidCount = 0L;
        this.rejectedCount = 0L;
    }

    public ExpenseSummaryResponse(Double totalExpenses, Double pending, Double paid, Double rejected) {
        this.totalExpenses = totalExpenses;
        this.pending = pending;
        this.paid = paid;
        this.rejected = rejected;
    }

    // Getters and Setters
    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public Double getPending() {
        return pending;
    }

    public void setPending(Double pending) {
        this.pending = pending;
    }

    public Double getPaid() {
        return paid;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }

    public Double getRejected() {
        return rejected;
    }

    public void setRejected(Double rejected) {
        this.rejected = rejected;
    }

    public Map<ExpenseCategory, Double> getCategoryBreakdown() {
        return categoryBreakdown;
    }

    public void setCategoryBreakdown(Map<ExpenseCategory, Double> categoryBreakdown) {
        this.categoryBreakdown = categoryBreakdown;
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

    public Long getPaidCount() {
        return paidCount;
    }

    public void setPaidCount(Long paidCount) {
        this.paidCount = paidCount;
    }

    public Long getRejectedCount() {
        return rejectedCount;
    }

    public void setRejectedCount(Long rejectedCount) {
        this.rejectedCount = rejectedCount;
    }
}