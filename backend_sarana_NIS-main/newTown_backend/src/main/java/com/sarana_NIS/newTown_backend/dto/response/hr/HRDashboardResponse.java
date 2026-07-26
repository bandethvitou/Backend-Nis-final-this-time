package com.sarana_NIS.newTown_backend.dto.response.hr;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HRDashboardResponse {

    private Integer totalStaff;
    private Integer activeStaff;
    private Integer onLeave;
    private Integer pendingLeaves;
    private Integer hiringRequests;
    private Integer birthdaysThisWeek;
    private Integer workAnniversaries;
    private List<DepartmentStat> byDepartment = new ArrayList<>();
    private List<LeaveStat> leaveStats = new ArrayList<>();
    private Double staffTurnoverRate;

    // Constructors
    public HRDashboardResponse() {}

    // Getters and Setters
    public Integer getTotalStaff() { return totalStaff; }
    public void setTotalStaff(Integer totalStaff) { this.totalStaff = totalStaff; }

    public Integer getActiveStaff() { return activeStaff; }
    public void setActiveStaff(Integer activeStaff) { this.activeStaff = activeStaff; }

    public Integer getOnLeave() { return onLeave; }
    public void setOnLeave(Integer onLeave) { this.onLeave = onLeave; }

    public Integer getPendingLeaves() { return pendingLeaves; }
    public void setPendingLeaves(Integer pendingLeaves) { this.pendingLeaves = pendingLeaves; }

    public Integer getHiringRequests() { return hiringRequests; }
    public void setHiringRequests(Integer hiringRequests) { this.hiringRequests = hiringRequests; }

    public Integer getBirthdaysThisWeek() { return birthdaysThisWeek; }
    public void setBirthdaysThisWeek(Integer birthdaysThisWeek) { this.birthdaysThisWeek = birthdaysThisWeek; }

    public Integer getWorkAnniversaries() { return workAnniversaries; }
    public void setWorkAnniversaries(Integer workAnniversaries) { this.workAnniversaries = workAnniversaries; }

    public List<DepartmentStat> getByDepartment() { return byDepartment; }
    public void setByDepartment(List<DepartmentStat> byDepartment) { this.byDepartment = byDepartment; }

    public List<LeaveStat> getLeaveStats() { return leaveStats; }
    public void setLeaveStats(List<LeaveStat> leaveStats) { this.leaveStats = leaveStats; }

    public Double getStaffTurnoverRate() { return staffTurnoverRate; }
    public void setStaffTurnoverRate(Double staffTurnoverRate) { this.staffTurnoverRate = staffTurnoverRate; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DepartmentStat {
        private String department;
        private Integer count;
        private Integer active;
        private Integer onLeave;

        // Constructors
        public DepartmentStat() {}

        // Getters and Setters
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }

        public Integer getCount() { return count; }
        public void setCount(Integer count) { this.count = count; }

        public Integer getActive() { return active; }
        public void setActive(Integer active) { this.active = active; }

        public Integer getOnLeave() { return onLeave; }
        public void setOnLeave(Integer onLeave) { this.onLeave = onLeave; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class LeaveStat {
        private String month;
        private Integer sickLeave;
        private Integer annualLeave;
        private Integer emergencyLeave;

        // Constructors
        public LeaveStat() {}

        // Getters and Setters
        public String getMonth() { return month; }
        public void setMonth(String month) { this.month = month; }

        public Integer getSickLeave() { return sickLeave; }
        public void setSickLeave(Integer sickLeave) { this.sickLeave = sickLeave; }

        public Integer getAnnualLeave() { return annualLeave; }
        public void setAnnualLeave(Integer annualLeave) { this.annualLeave = annualLeave; }

        public Integer getEmergencyLeave() { return emergencyLeave; }
        public void setEmergencyLeave(Integer emergencyLeave) { this.emergencyLeave = emergencyLeave; }
    }
}