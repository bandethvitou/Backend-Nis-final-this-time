package com.sarana_NIS.newTown_backend.dto.response.hr;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeaveSummaryResponse {

    private Integer totalRequests;
    private Integer pendingRequests;
    private Integer approvedRequests;
    private Integer deniedRequests;
    private Integer staffOnLeave;
    private List<LeaveTypeSummary> byType = new ArrayList<>();
    private List<DepartmentLeaveSummary> byDepartment = new ArrayList<>();
    private List<UpcomingLeave> upcomingLeaves = new ArrayList<>();

    // Constructors
    public LeaveSummaryResponse() {}

    public LeaveSummaryResponse(Integer totalRequests, Integer pendingRequests) {
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

    public Integer getDeniedRequests() { return deniedRequests; }
    public void setDeniedRequests(Integer deniedRequests) { this.deniedRequests = deniedRequests; }

    public Integer getStaffOnLeave() { return staffOnLeave; }
    public void setStaffOnLeave(Integer staffOnLeave) { this.staffOnLeave = staffOnLeave; }

    public List<LeaveTypeSummary> getByType() { return byType; }
    public void setByType(List<LeaveTypeSummary> byType) { this.byType = byType; }

    public List<DepartmentLeaveSummary> getByDepartment() { return byDepartment; }
    public void setByDepartment(List<DepartmentLeaveSummary> byDepartment) { this.byDepartment = byDepartment; }

    public List<UpcomingLeave> getUpcomingLeaves() { return upcomingLeaves; }
    public void setUpcomingLeaves(List<UpcomingLeave> upcomingLeaves) { this.upcomingLeaves = upcomingLeaves; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class LeaveTypeSummary {
        private String type;
        private Integer count;
        private Integer daysUsed;

        // Constructors
        public LeaveTypeSummary() {}

        // Getters and Setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public Integer getCount() { return count; }
        public void setCount(Integer count) { this.count = count; }

        public Integer getDaysUsed() { return daysUsed; }
        public void setDaysUsed(Integer daysUsed) { this.daysUsed = daysUsed; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DepartmentLeaveSummary {
        private String department;
        private Integer count;
        private Integer staffCount;
        private Double averageDaysPerStaff;

        // Constructors
        public DepartmentLeaveSummary() {}

        // Getters and Setters
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }

        public Integer getCount() { return count; }
        public void setCount(Integer count) { this.count = count; }

        public Integer getStaffCount() { return staffCount; }
        public void setStaffCount(Integer staffCount) { this.staffCount = staffCount; }

        public Double getAverageDaysPerStaff() { return averageDaysPerStaff; }
        public void setAverageDaysPerStaff(Double averageDaysPerStaff) { this.averageDaysPerStaff = averageDaysPerStaff; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UpcomingLeave {
        private Long id;
        private String staffName;
        private String department;
        private String type;
        private LocalDate startDate;
        private LocalDate endDate;
        private Integer days;

        // Constructors
        public UpcomingLeave() {}

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getStaffName() { return staffName; }
        public void setStaffName(String staffName) { this.staffName = staffName; }

        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public LocalDate getStartDate() { return startDate; }
        public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

        public LocalDate getEndDate() { return endDate; }
        public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

        public Integer getDays() { return days; }
        public void setDays(Integer days) { this.days = days; }
    }
}