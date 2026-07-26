package com.sarana_NIS.newTown_backend.dto.request.finance;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BillApprovalRequest {

    @NotNull(message = "Bill ID is required")
    private Long billId;

    @NotBlank(message = "Action is required")
    private String action; // APPROVE or REJECT

    private String reason;

    // Constructors
    public BillApprovalRequest() {
    }

    // Getters and Setters
    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}