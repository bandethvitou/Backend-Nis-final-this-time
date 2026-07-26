package com.sarana_NIS.newTown_backend.dto.response.counselling;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CounsellingDashboardResponse {

    private Integer activeCases;
    private Integer resolvedCases;
    private Integer totalSessions;
    private Integer atRiskStudents;
    private Integer highSeverity;
    private Integer mediumSeverity;
    private Integer lowSeverity;
    private List<RecentCase> recentCases = new ArrayList<>();
    private List<SessionStat> sessionsByType = new ArrayList<>();
    private List<IssueStat> issuesByType = new ArrayList<>();
    private Integer studentsNeedingFollowUp;
    private Integer appointmentsThisWeek;

    // Constructors
    public CounsellingDashboardResponse() {}

    // Getters and Setters
    public Integer getActiveCases() { return activeCases; }
    public void setActiveCases(Integer activeCases) { this.activeCases = activeCases; }

    public Integer getResolvedCases() { return resolvedCases; }
    public void setResolvedCases(Integer resolvedCases) { this.resolvedCases = resolvedCases; }

    public Integer getTotalSessions() { return totalSessions; }
    public void setTotalSessions(Integer totalSessions) { this.totalSessions = totalSessions; }

    public Integer getAtRiskStudents() { return atRiskStudents; }
    public void setAtRiskStudents(Integer atRiskStudents) { this.atRiskStudents = atRiskStudents; }

    public Integer getHighSeverity() { return highSeverity; }
    public void setHighSeverity(Integer highSeverity) { this.highSeverity = highSeverity; }

    public Integer getMediumSeverity() { return mediumSeverity; }
    public void setMediumSeverity(Integer mediumSeverity) { this.mediumSeverity = mediumSeverity; }

    public Integer getLowSeverity() { return lowSeverity; }
    public void setLowSeverity(Integer lowSeverity) { this.lowSeverity = lowSeverity; }

    public List<RecentCase> getRecentCases() { return recentCases; }
    public void setRecentCases(List<RecentCase> recentCases) { this.recentCases = recentCases; }

    public List<SessionStat> getSessionsByType() { return sessionsByType; }
    public void setSessionsByType(List<SessionStat> sessionsByType) { this.sessionsByType = sessionsByType; }

    public List<IssueStat> getIssuesByType() { return issuesByType; }
    public void setIssuesByType(List<IssueStat> issuesByType) { this.issuesByType = issuesByType; }

    public Integer getStudentsNeedingFollowUp() { return studentsNeedingFollowUp; }
    public void setStudentsNeedingFollowUp(Integer studentsNeedingFollowUp) { this.studentsNeedingFollowUp = studentsNeedingFollowUp; }

    public Integer getAppointmentsThisWeek() { return appointmentsThisWeek; }
    public void setAppointmentsThisWeek(Integer appointmentsThisWeek) { this.appointmentsThisWeek = appointmentsThisWeek; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RecentCase {
        private Long id;
        private String studentName;
        private String issueType;
        private String severity;
        private String status;
        private LocalDateTime createdAt;

        // Constructors
        public RecentCase() {}

        public RecentCase(Long id, String studentName) {
            this.id = id;
            this.studentName = studentName;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getStudentName() { return studentName; }
        public void setStudentName(String studentName) { this.studentName = studentName; }

        public String getIssueType() { return issueType; }
        public void setIssueType(String issueType) { this.issueType = issueType; }

        public String getSeverity() { return severity; }
        public void setSeverity(String severity) { this.severity = severity; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SessionStat {
        private String type;
        private Integer count;

        // Constructors
        public SessionStat() {}

        // Getters and Setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public Integer getCount() { return count; }
        public void setCount(Integer count) { this.count = count; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class IssueStat {
        private String issueType;
        private Integer count;
        private Double percentage;

        // Constructors
        public IssueStat() {}

        // Getters and Setters
        public String getIssueType() { return issueType; }
        public void setIssueType(String issueType) { this.issueType = issueType; }

        public Integer getCount() { return count; }
        public void setCount(Integer count) { this.count = count; }

        public Double getPercentage() { return percentage; }
        public void setPercentage(Double percentage) { this.percentage = percentage; }
    }
}