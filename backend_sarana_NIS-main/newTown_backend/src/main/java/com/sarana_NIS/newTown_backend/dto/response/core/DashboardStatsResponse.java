package com.sarana_NIS.newTown_backend.dto.response.core;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardStatsResponse {

    private QuickStats quickStats;
    private AcademicStats academicStats;
    private FinanceStats financeStats;
    private HRStats hrStats;
    private ITStats itStats;
    private List<RecentActivity> recentActivities = new ArrayList<>();
    private List<Alert> alerts = new ArrayList<>();

    // Constructors
    public DashboardStatsResponse() {}

    // Getters and Setters
    public QuickStats getQuickStats() { return quickStats; }
    public void setQuickStats(QuickStats quickStats) { this.quickStats = quickStats; }

    public AcademicStats getAcademicStats() { return academicStats; }
    public void setAcademicStats(AcademicStats academicStats) { this.academicStats = academicStats; }

    public FinanceStats getFinanceStats() { return financeStats; }
    public void setFinanceStats(FinanceStats financeStats) { this.financeStats = financeStats; }

    public HRStats getHrStats() { return hrStats; }
    public void setHrStats(HRStats hrStats) { this.hrStats = hrStats; }

    public ITStats getItStats() { return itStats; }
    public void setItStats(ITStats itStats) { this.itStats = itStats; }

    public List<RecentActivity> getRecentActivities() { return recentActivities; }
    public void setRecentActivities(List<RecentActivity> recentActivities) { this.recentActivities = recentActivities; }

    public List<Alert> getAlerts() { return alerts; }
    public void setAlerts(List<Alert> alerts) { this.alerts = alerts; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class QuickStats {
        private Integer totalStudents;
        private Integer totalStaff;
        private Integer totalTeachers;
        private Integer totalParents;
        private Integer totalClasses;
        private Integer totalDepartments;

        // Constructors
        public QuickStats() {}

        // Getters and Setters
        public Integer getTotalStudents() { return totalStudents; }
        public void setTotalStudents(Integer totalStudents) { this.totalStudents = totalStudents; }

        public Integer getTotalStaff() { return totalStaff; }
        public void setTotalStaff(Integer totalStaff) { this.totalStaff = totalStaff; }

        public Integer getTotalTeachers() { return totalTeachers; }
        public void setTotalTeachers(Integer totalTeachers) { this.totalTeachers = totalTeachers; }

        public Integer getTotalParents() { return totalParents; }
        public void setTotalParents(Integer totalParents) { this.totalParents = totalParents; }

        public Integer getTotalClasses() { return totalClasses; }
        public void setTotalClasses(Integer totalClasses) { this.totalClasses = totalClasses; }

        public Integer getTotalDepartments() { return totalDepartments; }
        public void setTotalDepartments(Integer totalDepartments) { this.totalDepartments = totalDepartments; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AcademicStats {
        private Integer presentToday;
        private Integer absentToday;
        private Integer lateToday;
        private Double overallAttendanceRate;
        private Double averageScore;
        private Integer atRiskStudents;
        private Integer homeworkPending;

        // Constructors
        public AcademicStats() {}

        // Getters and Setters
        public Integer getPresentToday() { return presentToday; }
        public void setPresentToday(Integer presentToday) { this.presentToday = presentToday; }

        public Integer getAbsentToday() { return absentToday; }
        public void setAbsentToday(Integer absentToday) { this.absentToday = absentToday; }

        public Integer getLateToday() { return lateToday; }
        public void setLateToday(Integer lateToday) { this.lateToday = lateToday; }

        public Double getOverallAttendanceRate() { return overallAttendanceRate; }
        public void setOverallAttendanceRate(Double overallAttendanceRate) { this.overallAttendanceRate = overallAttendanceRate; }

        public Double getAverageScore() { return averageScore; }
        public void setAverageScore(Double averageScore) { this.averageScore = averageScore; }

        public Integer getAtRiskStudents() { return atRiskStudents; }
        public void setAtRiskStudents(Integer atRiskStudents) { this.atRiskStudents = atRiskStudents; }

        public Integer getHomeworkPending() { return homeworkPending; }
        public void setHomeworkPending(Integer homeworkPending) { this.homeworkPending = homeworkPending; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FinanceStats {
        private Double totalFeesCollected;
        private Double totalFeesOutstanding;
        private Double totalExpenses;
        private Double totalBillsPending;
        private Double totalSalariesPaid;
        private Integer feeDefaultersCount;

        // Constructors
        public FinanceStats() {}

        // Getters and Setters
        public Double getTotalFeesCollected() { return totalFeesCollected; }
        public void setTotalFeesCollected(Double totalFeesCollected) { this.totalFeesCollected = totalFeesCollected; }

        public Double getTotalFeesOutstanding() { return totalFeesOutstanding; }
        public void setTotalFeesOutstanding(Double totalFeesOutstanding) { this.totalFeesOutstanding = totalFeesOutstanding; }

        public Double getTotalExpenses() { return totalExpenses; }
        public void setTotalExpenses(Double totalExpenses) { this.totalExpenses = totalExpenses; }

        public Double getTotalBillsPending() { return totalBillsPending; }
        public void setTotalBillsPending(Double totalBillsPending) { this.totalBillsPending = totalBillsPending; }

        public Double getTotalSalariesPaid() { return totalSalariesPaid; }
        public void setTotalSalariesPaid(Double totalSalariesPaid) { this.totalSalariesPaid = totalSalariesPaid; }

        public Integer getFeeDefaultersCount() { return feeDefaultersCount; }
        public void setFeeDefaultersCount(Integer feeDefaultersCount) { this.feeDefaultersCount = feeDefaultersCount; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class HRStats {
        private Integer leaveRequestsPending;
        private Integer hiringRequestsPending;
        private Integer staffOnLeave;
        private Integer birthdaysThisWeek;

        // Constructors
        public HRStats() {}

        // Getters and Setters
        public Integer getLeaveRequestsPending() { return leaveRequestsPending; }
        public void setLeaveRequestsPending(Integer leaveRequestsPending) { this.leaveRequestsPending = leaveRequestsPending; }

        public Integer getHiringRequestsPending() { return hiringRequestsPending; }
        public void setHiringRequestsPending(Integer hiringRequestsPending) { this.hiringRequestsPending = hiringRequestsPending; }

        public Integer getStaffOnLeave() { return staffOnLeave; }
        public void setStaffOnLeave(Integer staffOnLeave) { this.staffOnLeave = staffOnLeave; }

        public Integer getBirthdaysThisWeek() { return birthdaysThisWeek; }
        public void setBirthdaysThisWeek(Integer birthdaysThisWeek) { this.birthdaysThisWeek = birthdaysThisWeek; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ITStats {
        private Integer openTickets;
        private Integer equipmentUnderRepair;
        private Integer serversOnline;
        private Double systemUptime;

        // Constructors
        public ITStats() {}

        // Getters and Setters
        public Integer getOpenTickets() { return openTickets; }
        public void setOpenTickets(Integer openTickets) { this.openTickets = openTickets; }

        public Integer getEquipmentUnderRepair() { return equipmentUnderRepair; }
        public void setEquipmentUnderRepair(Integer equipmentUnderRepair) { this.equipmentUnderRepair = equipmentUnderRepair; }

        public Integer getServersOnline() { return serversOnline; }
        public void setServersOnline(Integer serversOnline) { this.serversOnline = serversOnline; }

        public Double getSystemUptime() { return systemUptime; }
        public void setSystemUptime(Double systemUptime) { this.systemUptime = systemUptime; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RecentActivity {
        private String type;
        private String description;
        private String user;
        private String avatar;
        private LocalDateTime timestamp;
        private String action;

        // Constructors
        public RecentActivity() {}

        // Getters and Setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getUser() { return user; }
        public void setUser(String user) { this.user = user; }

        public String getAvatar() { return avatar; }
        public void setAvatar(String avatar) { this.avatar = avatar; }

        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Alert {
        private String type;
        private String message;
        private String action;
        private String link;
        private Boolean isRead;

        // Constructors
        public Alert() {}

        // Getters and Setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }

        public String getLink() { return link; }
        public void setLink(String link) { this.link = link; }

        public Boolean getIsRead() { return isRead; }
        public void setIsRead(Boolean isRead) { this.isRead = isRead; }
    }
}