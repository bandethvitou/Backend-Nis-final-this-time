package com.sarana_NIS.newTown_backend.dto.response.position;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PositionAssignmentResultResponse {

    private Long staffId;
    private String staffName;
    private List<AssignedPosition> assigned = new ArrayList<>();
    private List<RemovedPosition> removed = new ArrayList<>();
    private String message;
    private Boolean success;
    private List<String> warnings = new ArrayList<>();

    // Constructors
    public PositionAssignmentResultResponse() {}

    public PositionAssignmentResultResponse(Long staffId, String staffName, Boolean success) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.success = success;
    }

    // Getters and Setters
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }

    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }

    public List<AssignedPosition> getAssigned() { return assigned; }
    public void setAssigned(List<AssignedPosition> assigned) { this.assigned = assigned; }

    public List<RemovedPosition> getRemoved() { return removed; }
    public void setRemoved(List<RemovedPosition> removed) { this.removed = removed; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }

    public List<String> getWarnings() { return warnings; }
    public void setWarnings(List<String> warnings) { this.warnings = warnings; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AssignedPosition {
        private Long positionId;
        private String positionName;
        private Boolean isTemporary;
        private LocalDate startDate;
        private LocalDate endDate;
        private String status;

        // Constructors
        public AssignedPosition() {}

        public AssignedPosition(Long positionId, String positionName) {
            this.positionId = positionId;
            this.positionName = positionName;
        }

        // Getters and Setters
        public Long getPositionId() { return positionId; }
        public void setPositionId(Long positionId) { this.positionId = positionId; }

        public String getPositionName() { return positionName; }
        public void setPositionName(String positionName) { this.positionName = positionName; }

        public Boolean getIsTemporary() { return isTemporary; }
        public void setIsTemporary(Boolean isTemporary) { this.isTemporary = isTemporary; }

        public LocalDate getStartDate() { return startDate; }
        public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

        public LocalDate getEndDate() { return endDate; }
        public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RemovedPosition {
        private Long positionId;
        private String positionName;
        private String reason;
        private String status;

        // Constructors
        public RemovedPosition() {}

        public RemovedPosition(Long positionId, String positionName) {
            this.positionId = positionId;
            this.positionName = positionName;
        }

        // Getters and Setters
        public Long getPositionId() { return positionId; }
        public void setPositionId(Long positionId) { this.positionId = positionId; }

        public String getPositionName() { return positionName; }
        public void setPositionName(String positionName) { this.positionName = positionName; }

        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}