package com.sarana_NIS.newTown_backend.dto.request.position;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class StaffPositionRequest {

    @NotNull(message = "Staff ID is required")
    private Long staffId;

    @NotNull(message = "Position ID is required")
    private Long positionId;

    private Boolean isTemporary;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @Future(message = "End date must be in the future")
    private LocalDate endDate;

    @Size(max = 500, message = "Notes must be less than 500 characters")
    private String notes;

    // Constructors
    public StaffPositionRequest() {}

    public StaffPositionRequest(Long staffId, Long positionId, LocalDate startDate) {
        this.staffId = staffId;
        this.positionId = positionId;
        this.startDate = startDate;
    }

    // Getters and Setters
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }

    public Long getPositionId() { return positionId; }
    public void setPositionId(Long positionId) { this.positionId = positionId; }

    public Boolean getIsTemporary() { return isTemporary; }
    public void setIsTemporary(Boolean isTemporary) { this.isTemporary = isTemporary; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}