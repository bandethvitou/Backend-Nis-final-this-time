package com.sarana_NIS.newTown_backend.dto.request.hr;

import com.sarana_NIS.newTown_backend.model.enums.LeaveType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class LeaveRequestDTO {

    @NotNull(message = "Staff ID is required")
    private Long staffId;

    @NotNull(message = "Leave type is required")
    private LeaveType leaveType;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    private LocalDate endDate;

    @Size(max = 500, message = "Reason must be less than 500 characters")
    private String reason;

    // Constructors
    public LeaveRequestDTO() {}

    public LeaveRequestDTO(Long staffId, LeaveType leaveType, LocalDate startDate, LocalDate endDate) {
        this.staffId = staffId;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }

    public LeaveType getLeaveType() { return leaveType; }
    public void setLeaveType(LeaveType leaveType) { this.leaveType = leaveType; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}