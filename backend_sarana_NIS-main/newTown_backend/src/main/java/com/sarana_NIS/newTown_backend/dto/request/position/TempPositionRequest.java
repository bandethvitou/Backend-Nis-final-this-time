package com.sarana_NIS.newTown_backend.dto.request.position;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TempPositionRequest {

    @NotNull(message = "Staff ID is required")
    private Long staffId;

    private Long positionId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @Future(message = "End date must be in the future")
    private LocalDate endDate;

    @Size(max = 500, message = "Reason must be less than 500 characters")
    private String reason;

    private List<BulkTempPosition> bulkPositions = new ArrayList<>();

    // Constructors
    public TempPositionRequest() {}

    public TempPositionRequest(Long staffId, LocalDate startDate, LocalDate endDate) {
        this.staffId = staffId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }

    public Long getPositionId() { return positionId; }
    public void setPositionId(Long positionId) { this.positionId = positionId; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public List<BulkTempPosition> getBulkPositions() { return bulkPositions; }
    public void setBulkPositions(List<BulkTempPosition> bulkPositions) { this.bulkPositions = bulkPositions; }

    // Inner class
    public static class BulkTempPosition {
        @NotNull(message = "Staff ID is required")
        private Long staffId;

        @NotNull(message = "Position ID is required")
        private Long positionId;

        @NotNull(message = "Start date is required")
        private LocalDate startDate;

        @Future(message = "End date must be in the future")
        private LocalDate endDate;

        // Constructors
        public BulkTempPosition() {}

        public BulkTempPosition(Long staffId, Long positionId, LocalDate startDate, LocalDate endDate) {
            this.staffId = staffId;
            this.positionId = positionId;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        // Getters and Setters
        public Long getStaffId() { return staffId; }
        public void setStaffId(Long staffId) { this.staffId = staffId; }

        public Long getPositionId() { return positionId; }
        public void setPositionId(Long positionId) { this.positionId = positionId; }

        public LocalDate getStartDate() { return startDate; }
        public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

        public LocalDate getEndDate() { return endDate; }
        public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    }
}