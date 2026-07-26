package com.sarana_NIS.newTown_backend.dto.response.hr;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HiringSummaryResponse {

    private Integer totalRequests;
    private Integer pendingRequests;
    private Integer approvedRequests;
    private Integer rejectedRequests;
    private List<PositionHiringSummary> byPosition = new ArrayList<>();
    private List<DepartmentHiringSummary> byDepartment = new ArrayList<>();
    private Integer positionsFilled;
    private Integer positionsOpen;

    // Constructors
    public HiringSummaryResponse() {}

    public HiringSummaryResponse(Integer totalRequests, Integer pendingRequests) {
        this.totalRequests = totalRequests;
        this.pendingRequests = pendingRequests;
    }

    // Getters and Setters
    public Integer getTotalRequests() { return totalRequests; }
    public void setTotalRequests(Integer totalRequests) { this.totalRequests = totalRequests; }

    public Integer getPendingRequests() { return pendingRequests; }
    public void setPendingRequests(Integer pendingRequests) { this.pendingRequests = pendingRequests; }

    public Integer getApprovedRequests() { return approvedRequests; }
    public void setApprovedRequests(Integer approvedRequests) { this.approvedRequests = approvedRequests; }

    public Integer getRejectedRequests() { return rejectedRequests; }
    public void setRejectedRequests(Integer rejectedRequests) { this.rejectedRequests = rejectedRequests; }

    public List<PositionHiringSummary> getByPosition() { return byPosition; }
    public void setByPosition(List<PositionHiringSummary> byPosition) { this.byPosition = byPosition; }

    public List<DepartmentHiringSummary> getByDepartment() { return byDepartment; }
    public void setByDepartment(List<DepartmentHiringSummary> byDepartment) { this.byDepartment = byDepartment; }

    public Integer getPositionsFilled() { return positionsFilled; }
    public void setPositionsFilled(Integer positionsFilled) { this.positionsFilled = positionsFilled; }

    public Integer getPositionsOpen() { return positionsOpen; }
    public void setPositionsOpen(Integer positionsOpen) { this.positionsOpen = positionsOpen; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PositionHiringSummary {
        private String position;
        private Integer requested;
        private Integer approved;
        private Integer filled;

        // Constructors
        public PositionHiringSummary() {}

        // Getters and Setters
        public String getPosition() { return position; }
        public void setPosition(String position) { this.position = position; }

        public Integer getRequested() { return requested; }
        public void setRequested(Integer requested) { this.requested = requested; }

        public Integer getApproved() { return approved; }
        public void setApproved(Integer approved) { this.approved = approved; }

        public Integer getFilled() { return filled; }
        public void setFilled(Integer filled) { this.filled = filled; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DepartmentHiringSummary {
        private String department;
        private Integer requests;
        private Integer approved;
        private Integer filled;

        // Constructors
        public DepartmentHiringSummary() {}

        // Getters and Setters
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }

        public Integer getRequests() { return requests; }
        public void setRequests(Integer requests) { this.requests = requests; }

        public Integer getApproved() { return approved; }
        public void setApproved(Integer approved) { this.approved = approved; }

        public Integer getFilled() { return filled; }
        public void setFilled(Integer filled) { this.filled = filled; }
    }
}