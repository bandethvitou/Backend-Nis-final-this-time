package com.sarana_NIS.newTown_backend.dto.request.finance;

import jakarta.validation.constraints.NotNull;

public class ExpenseApprovalRequest {

    @NotNull(message = "Expense ID is required")
    private Long expenseId;

    @NotNull(message = "Action is required")
    private String action;

    private String reason;
    private String notes;

    // Constructors
    public ExpenseApprovalRequest() {}

    public ExpenseApprovalRequest(Long expenseId, String action) {
        this.expenseId = expenseId;
        this.action = action;
    }

    // Getters and Setters
    public Long getExpenseId() { return expenseId; }
    public void setExpenseId(Long expenseId) { this.expenseId = expenseId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}