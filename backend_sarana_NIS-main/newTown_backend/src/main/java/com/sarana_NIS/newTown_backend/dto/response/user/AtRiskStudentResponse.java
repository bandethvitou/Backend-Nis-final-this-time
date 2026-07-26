package com.sarana_NIS.newTown_backend.dto.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarana_NIS.newTown_backend.model.enums.IssueSeverity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AtRiskStudentResponse {

    private Long studentId;
    private String studentName;
    private String className;
    private String level;
    private Double gpa;
    private Integer attendanceRate;
    private IssueSeverity severity;
    private List<RiskFactor> riskFactors = new ArrayList<>();
    private List<ActionPlan> actionPlans = new ArrayList<>();
    private String status;
    private LocalDateTime lastAssessment;
    private String assignedCounsellor;

    // Constructors
    public AtRiskStudentResponse() {}

    public AtRiskStudentResponse(Long studentId, String studentName) {
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

    public IssueSeverity getSeverity() { return severity; }
    public void setSeverity(IssueSeverity severity) { this.severity = severity; }

    public List<RiskFactor> getRiskFactors() { return riskFactors; }
    public void setRiskFactors(List<RiskFactor> riskFactors) { this.riskFactors = riskFactors; }

    public List<ActionPlan> getActionPlans() { return actionPlans; }
    public void setActionPlans(List<ActionPlan> actionPlans) { this.actionPlans = actionPlans; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getLastAssessment() { return lastAssessment; }
    public void setLastAssessment(LocalDateTime lastAssessment) { this.lastAssessment = lastAssessment; }

    public String getAssignedCounsellor() { return assignedCounsellor; }
    public void setAssignedCounsellor(String assignedCounsellor) { this.assignedCounsellor = assignedCounsellor; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RiskFactor {
        private String factor;
        private String description;
        private IssueSeverity severity;
        private LocalDateTime identifiedAt;

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
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ActionPlan {
        private String action;
        private String responsible;
        private LocalDate dueDate;
        private String status;

        // Constructors
        public ActionPlan() {}

        // Getters and Setters
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }

        public String getResponsible() { return responsible; }
        public void setResponsible(String responsible) { this.responsible = responsible; }

        public LocalDate getDueDate() { return dueDate; }
        public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}