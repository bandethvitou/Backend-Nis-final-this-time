package com.sarana_NIS.newTown_backend.dto.request.position;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PositionAssignmentRequest {

    @NotNull(message = "Staff ID is required")
    private Long staffId;

    private List<PositionAssignment> positions = new ArrayList<>();
    private List<PositionRemoval> removePositions = new ArrayList<>();

    @Size(max = 500, message = "Reason must be less than 500 characters")
    private String reason;

    // Constructors
    public PositionAssignmentRequest() {}

    public PositionAssignmentRequest(Long staffId) {
        this.staffId = staffId;
    }

    // Getters and Setters
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }

    public List<PositionAssignment> getPositions() { return positions; }
    public void setPositions(List<PositionAssignment> positions) { this.positions = positions; }

    public List<PositionRemoval> getRemovePositions() { return removePositions; }
    public void setRemovePositions(List<PositionRemoval> removePositions) { this.removePositions = removePositions; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    // Inner classes
    public static class PositionAssignment {
        @NotNull(message = "Position ID is required")
        private Long positionId;

        private Boolean isTemporary;

        @NotNull(message = "Start date is required")
        private LocalDate startDate;

        @Future(message = "End date must be in the future")
        private LocalDate endDate;

        // Constructors
        public PositionAssignment() {}

        public PositionAssignment(Long positionId, LocalDate startDate) {
            this.positionId = positionId;
            this.startDate = startDate;
        }

        // Getters and Setters
        public Long getPositionId() { return positionId; }
        public void setPositionId(Long positionId) { this.positionId = positionId; }

        public Boolean getIsTemporary() { return isTemporary; }
        public void setIsTemporary(Boolean isTemporary) { this.isTemporary = isTemporary; }

        public LocalDate getStartDate() { return startDate; }
        public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

        public LocalDate getEndDate() { return endDate; }
        public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    }

    public static class PositionRemoval {
        @NotNull(message = "Staff position ID is required")
        private Long staffPositionId;

        @Size(max = 500, message = "Reason must be less than 500 characters")
        private String reason;

        // Constructors
        public PositionRemoval() {}

        public PositionRemoval(Long staffPositionId) {
            this.staffPositionId = staffPositionId;
        }

        // Getters and Setters
        public Long getStaffPositionId() { return staffPositionId; }
        public void setStaffPositionId(Long staffPositionId) { this.staffPositionId = staffPositionId; }

        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }
    }
}