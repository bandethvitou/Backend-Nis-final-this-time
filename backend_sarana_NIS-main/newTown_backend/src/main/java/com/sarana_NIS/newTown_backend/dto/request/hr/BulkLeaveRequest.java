package com.sarana_NIS.newTown_backend.dto.request.hr;

import com.sarana_NIS.newTown_backend.model.enums.LeaveType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BulkLeaveRequest {

    @NotEmpty(message = "Staff IDs cannot be empty")
    private List<Long> staffIds = new ArrayList<>();

    @NotNull(message = "Leave type is required")
    private LeaveType leaveType;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @Size(max = 500, message = "Reason must be less than 500 characters")
    private String reason;

    // Constructors
    public BulkLeaveRequest() {}

    public BulkLeaveRequest(List<Long> staffIds, LeaveType leaveType, LocalDate startDate, LocalDate endDate) {
        this.staffIds = staffIds;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public List<Long> getStaffIds() { return staffIds; }
    public void setStaffIds(List<Long> staffIds) { this.staffIds = staffIds; }

    public LeaveType getLeaveType() { return leaveType; }
    public void setLeaveType(LeaveType leaveType) { this.leaveType = leaveType; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}