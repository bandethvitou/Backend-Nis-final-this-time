package com.sarana_NIS.newTown_backend.dto.response.position;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StaffPositionResponse {

    private Long id;  // ✅ This field exists
    private Long staffId;
    private String staffName;
    private String staffCode;
    private Long positionId;
    private String positionName;
    private String positionCategory;
    private Boolean isTemporary;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
    private String assignedBy;
    private String assignedByName;
    private String notes;
    private String status;
    private Integer daysRemaining;
    private String statusColor;

    // Constructors
    public StaffPositionResponse() {}

    public StaffPositionResponse(Long id, Long staffId, String staffName, Long positionId, String positionName) {
        this.id = id;
        this.staffId = staffId;
        this.staffName = staffName;
        this.positionId = positionId;
        this.positionName = positionName;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }

    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }

    public String getStaffCode() { return staffCode; }
    public void setStaffCode(String staffCode) { this.staffCode = staffCode; }

    public Long getPositionId() { return positionId; }
    public void setPositionId(Long positionId) { this.positionId = positionId; }

    public String getPositionName() { return positionName; }
    public void setPositionName(String positionName) { this.positionName = positionName; }

    public String getPositionCategory() { return positionCategory; }
    public void setPositionCategory(String positionCategory) { this.positionCategory = positionCategory; }

    public Boolean getIsTemporary() { return isTemporary; }
    public void setIsTemporary(Boolean isTemporary) { this.isTemporary = isTemporary; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public String getAssignedBy() { return assignedBy; }
    public void setAssignedBy(String assignedBy) { this.assignedBy = assignedBy; }

    public String getAssignedByName() { return assignedByName; }
    public void setAssignedByName(String assignedByName) { this.assignedByName = assignedByName; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getDaysRemaining() { return daysRemaining; }
    public void setDaysRemaining(Integer daysRemaining) { this.daysRemaining = daysRemaining; }

    public String getStatusColor() { return statusColor; }
    public void setStatusColor(String statusColor) { this.statusColor = statusColor; }
}