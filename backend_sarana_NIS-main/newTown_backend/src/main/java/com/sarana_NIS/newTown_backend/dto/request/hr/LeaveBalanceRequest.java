package com.sarana_NIS.newTown_backend.dto.request.hr;

import jakarta.validation.constraints.NotNull;

public class LeaveBalanceRequest {

    @NotNull(message = "Staff ID is required")
    private Long staffId;

    private String year;

    // Constructors
    public LeaveBalanceRequest() {}

    public LeaveBalanceRequest(Long staffId) {
        this.staffId = staffId;
    }

    // Getters and Setters
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }
}