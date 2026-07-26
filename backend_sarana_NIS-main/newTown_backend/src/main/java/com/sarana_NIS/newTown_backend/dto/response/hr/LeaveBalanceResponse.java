package com.sarana_NIS.newTown_backend.dto.response.hr;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeaveBalanceResponse {

    private Long staffId;
    private String staffName;
    private String year;
    private Map<String, LeaveTypeBalance> balances = new HashMap<>();
    private Integer totalDaysUsed;
    private Integer totalDaysRemaining;
    private Integer pendingDays;

    // Constructors
    public LeaveBalanceResponse() {}

    public LeaveBalanceResponse(Long staffId, String staffName) {
        this.staffId = staffId;
        this.staffName = staffName;
    }

    // Getters and Setters
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }

    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public Map<String, LeaveTypeBalance> getBalances() { return balances; }
    public void setBalances(Map<String, LeaveTypeBalance> balances) { this.balances = balances; }

    public Integer getTotalDaysUsed() { return totalDaysUsed; }
    public void setTotalDaysUsed(Integer totalDaysUsed) { this.totalDaysUsed = totalDaysUsed; }

    public Integer getTotalDaysRemaining() { return totalDaysRemaining; }
    public void setTotalDaysRemaining(Integer totalDaysRemaining) { this.totalDaysRemaining = totalDaysRemaining; }

    public Integer getPendingDays() { return pendingDays; }
    public void setPendingDays(Integer pendingDays) { this.pendingDays = pendingDays; }

    // Inner class
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class LeaveTypeBalance {
        private String type;
        private Integer allotted;
        private Integer used;
        private Integer remaining;
        private Integer pending;
        private String status;
        private String statusColor;

        // Constructors
        public LeaveTypeBalance() {}

        public LeaveTypeBalance(String type, Integer allotted, Integer used) {
            this.type = type;
            this.allotted = allotted;
            this.used = used;
        }

        // Getters and Setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public Integer getAllotted() { return allotted; }
        public void setAllotted(Integer allotted) { this.allotted = allotted; }

        public Integer getUsed() { return used; }
        public void setUsed(Integer used) { this.used = used; }

        public Integer getRemaining() { return remaining; }
        public void setRemaining(Integer remaining) { this.remaining = remaining; }

        public Integer getPending() { return pending; }
        public void setPending(Integer pending) { this.pending = pending; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getStatusColor() { return statusColor; }
        public void setStatusColor(String statusColor) { this.statusColor = statusColor; }
    }
}