package com.sarana_NIS.newTown_backend.dto.request.hr;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class SalaryAdjustmentRequest {

    @NotNull(message = "Staff ID is required")
    private Long staffId;

    @NotNull(message = "Adjustment type is required")
    private String adjustmentType;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amount;

    @Size(max = 500, message = "Reason must be less than 500 characters")
    private String reason;

    private Boolean isOneTime = true;
    private String effectiveDate;

    // Constructors
    public SalaryAdjustmentRequest() {}

    public SalaryAdjustmentRequest(Long staffId, String adjustmentType, BigDecimal amount) {
        this.staffId = staffId;
        this.adjustmentType = adjustmentType;
        this.amount = amount;
    }

    // Getters and Setters
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }

    public String getAdjustmentType() { return adjustmentType; }
    public void setAdjustmentType(String adjustmentType) { this.adjustmentType = adjustmentType; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Boolean getIsOneTime() { return isOneTime; }
    public void setIsOneTime(Boolean isOneTime) { this.isOneTime = isOneTime; }

    public String getEffectiveDate() { return effectiveDate; }
    public void setEffectiveDate(String effectiveDate) { this.effectiveDate = effectiveDate; }
}