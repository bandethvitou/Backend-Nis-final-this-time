package com.sarana_NIS.newTown_backend.dto.response.counselling;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarana_NIS.newTown_backend.model.enums.IssueSeverity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AtRiskStudentDetailResponse {

    private Long studentId;
    private String studentName;
    private String className;
    private String level;
    private Double gpa;
    private Integer attendanceRate;
    private Integer behavioralIncidents;
    private List<RiskFactor> riskFactors = new ArrayList<>();
    private IssueSeverity overallSeverity;
    private List<CaseSummary> activeCases = new ArrayList<>();
    private String recommendedActions;
    private LocalDateTime lastAssessment;

    // Constructors
    public AtRiskStudentDetailResponse() {}

    public AtRiskStudentDetailResponse(Long studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }

    // Getters and Setters
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public Double getGpa() { return gpa; }
    public void setGpa(Double gpa) { this.gpa = gpa; }

    public Integer getAttendanceRate() { return attendanceRate; }
    public void setAttendanceRate(Integer attendanceRate) { this.attendanceRate = attendanceRate; }

    public Integer getBehavioralIncidents() { return behavioralIncidents; }
    public void setBehavioralIncidents(Integer behavioralIncidents) { this.behavioralIncidents = behavioralIncidents; }

    public List<RiskFactor> getRiskFactors() { return riskFactors; }
    public void setRiskFactors(List<RiskFactor> riskFactors) { this.riskFactors = riskFactors; }

    public IssueSeverity getOverallSeverity() { return overallSeverity; }
    public void setOverallSeverity(IssueSeverity overallSeverity) { this.overallSeverity = overallSeverity; }

    public List<CaseSummary> getActiveCases() { return activeCases; }
    public void setActiveCases(List<CaseSummary> activeCases) { this.activeCases = activeCases; }

    public String getRecommendedActions() { return recommendedActions; }
    public void setRecommendedActions(String recommendedActions) { this.recommendedActions = recommendedActions; }

    public LocalDateTime getLastAssessment() { return lastAssessment; }
    public void setLastAssessment(LocalDateTime lastAssessment) { this.lastAssessment = lastAssessment; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RiskFactor {
        private String factor;
        private String description;
        private IssueSeverity severity;
        private LocalDateTime identifiedAt;
        private String identifiedBy;

        // Constructors
        public RiskFactor() {}

        // Getters and Setters
        public String getFactor() { return factor; }
        public void setFactor(String factor) { this.factor = factor; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public IssueSeverity getSeverity() { return severity; }
        public void setSeverity(IssueSeverity severity) { this.severity = severity; }

        public LocalDateTime getIdentifiedAt() { return identifiedAt; }
        public void setIdentifiedAt(LocalDateTime identifiedAt) { this.identifiedAt = identifiedAt; }

        public String getIdentifiedBy() { return identifiedBy; }
        public void setIdentifiedBy(String identifiedBy) { this.identifiedBy = identifiedBy; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CaseSummary {
        private Long id;
        private String title;
        private String status;
        private LocalDateTime openedAt;
        private String assignedTo;

        // Constructors
        public CaseSummary() {}

        public CaseSummary(Long id, String title) {
            this.id = id;
            this.title = title;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public LocalDateTime getOpenedAt() { return openedAt; }
        public void setOpenedAt(LocalDateTime openedAt) { this.openedAt = openedAt; }

        public String getAssignedTo() { return assignedTo; }
        public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }
    }
}