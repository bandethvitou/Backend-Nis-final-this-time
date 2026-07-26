package com.sarana_NIS.newTown_backend.dto.response.position;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarana_NIS.newTown_backend.model.enums.TempRoleStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TempRoleStatusResponse {

    private Long staffId;
    private String staffName;
    private List<TempPositionDetail> positions = new ArrayList<>();
    private TempRoleStatus overallStatus;
    private Integer expiringCount;
    private Integer expiredCount;
    private String nextAction;

    // Constructors
    public TempRoleStatusResponse() {}

    public TempRoleStatusResponse(Long staffId, String staffName) {
        this.staffId = staffId;
        this.staffName = staffName;
    }

    // Getters and Setters
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }

    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }

    public List<TempPositionDetail> getPositions() { return positions; }
    public void setPositions(List<TempPositionDetail> positions) { this.positions = positions; }

    public TempRoleStatus getOverallStatus() { return overallStatus; }
    public void setOverallStatus(TempRoleStatus overallStatus) { this.overallStatus = overallStatus; }

    public Integer getExpiringCount() { return expiringCount; }
    public void setExpiringCount(Integer expiringCount) { this.expiringCount = expiringCount; }

    public Integer getExpiredCount() { return expiredCount; }
    public void setExpiredCount(Integer expiredCount) { this.expiredCount = expiredCount; }

    public String getNextAction() { return nextAction; }
    public void setNextAction(String nextAction) { this.nextAction = nextAction; }

    // Inner class
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TempPositionDetail {
        private Long staffPositionId;
        private String positionName;
        private LocalDate startDate;
        private LocalDate endDate;
        private TempRoleStatus status;
        private String statusColor;
        private Integer daysRemaining;
        private String actionRequired;

        // Constructors
        public TempPositionDetail() {}

        public TempPositionDetail(Long staffPositionId, String positionName) {
            this.staffPositionId = staffPositionId;
            this.positionName = positionName;
        }

        // Getters and Setters
        public Long getStaffPositionId() { return staffPositionId; }
        public void setStaffPositionId(Long staffPositionId) { this.staffPositionId = staffPositionId; }

        public String getPositionName() { return positionName; }
        public void setPositionName(String positionName) { this.positionName = positionName; }

        public LocalDate getStartDate() { return startDate; }
        public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

        public LocalDate getEndDate() { return endDate; }
        public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

        public TempRoleStatus getStatus() { return status; }
        public void setStatus(TempRoleStatus status) { this.status = status; }

        public String getStatusColor() { return statusColor; }
        public void setStatusColor(String statusColor) { this.statusColor = statusColor; }

        public Integer getDaysRemaining() { return daysRemaining; }
        public void setDaysRemaining(Integer daysRemaining) { this.daysRemaining = daysRemaining; }

        public String getActionRequired() { return actionRequired; }
        public void setActionRequired(String actionRequired) { this.actionRequired = actionRequired; }
    }
}