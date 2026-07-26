package com.sarana_NIS.newTown_backend.dto.response.position;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StaffPositionSummaryResponse {

    private Long staffId;
    private String staffName;
    private String staffCode;
    private List<PositionDetail> currentPositions = new ArrayList<>();
    private List<PositionDetail> previousPositions = new ArrayList<>();
    private Integer totalPositions;
    private Boolean hasTemporaryPositions;
    private Integer tempPositionsCount;

    // Constructors
    public StaffPositionSummaryResponse() {}

    public StaffPositionSummaryResponse(Long staffId, String staffName, String staffCode) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.staffCode = staffCode;
    }

    // Getters and Setters
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }

    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }

    public String getStaffCode() { return staffCode; }
    public void setStaffCode(String staffCode) { this.staffCode = staffCode; }

    public List<PositionDetail> getCurrentPositions() { return currentPositions; }
    public void setCurrentPositions(List<PositionDetail> currentPositions) {
        this.currentPositions = currentPositions != null ? currentPositions : new ArrayList<>();
    }

    public List<PositionDetail> getPreviousPositions() { return previousPositions; }
    public void setPreviousPositions(List<PositionDetail> previousPositions) {
        this.previousPositions = previousPositions != null ? previousPositions : new ArrayList<>();
    }

    public Integer getTotalPositions() { return totalPositions; }
    public void setTotalPositions(Integer totalPositions) { this.totalPositions = totalPositions; }

    public Boolean getHasTemporaryPositions() { return hasTemporaryPositions; }
    public void setHasTemporaryPositions(Boolean hasTemporaryPositions) {
        this.hasTemporaryPositions = hasTemporaryPositions;
    }

    public Integer getTempPositionsCount() { return tempPositionsCount; }
    public void setTempPositionsCount(Integer tempPositionsCount) {
        this.tempPositionsCount = tempPositionsCount;
    }

    // Inner class
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PositionDetail {
        private String positionName;
        private String category;
        private Boolean isTemporary;
        private LocalDate startDate;
        private LocalDate endDate;
        private String assignedBy;
        private String status;
        private String statusColor;
        private Integer daysRemaining;

        // Constructors
        public PositionDetail() {}

        public PositionDetail(String positionName) {
            this.positionName = positionName;
        }

        // Getters and Setters
        public String getPositionName() { return positionName; }
        public void setPositionName(String positionName) { this.positionName = positionName; }

        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }

        public Boolean getIsTemporary() { return isTemporary; }
        public void setIsTemporary(Boolean isTemporary) { this.isTemporary = isTemporary; }

        public LocalDate getStartDate() { return startDate; }
        public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

        public LocalDate getEndDate() { return endDate; }
        public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

        public String getAssignedBy() { return assignedBy; }
        public void setAssignedBy(String assignedBy) { this.assignedBy = assignedBy; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getStatusColor() { return statusColor; }
        public void setStatusColor(String statusColor) { this.statusColor = statusColor; }

        public Integer getDaysRemaining() { return daysRemaining; }
        public void setDaysRemaining(Integer daysRemaining) { this.daysRemaining = daysRemaining; }
    }
}