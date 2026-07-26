package com.sarana_NIS.newTown_backend.dto.response.academic;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomeworkSummaryResponse {

    private String className;
    private String subjectName;
    private String byStatus;

    private Integer totalHomework;
    private Integer pendingHomework;
    private Integer submittedHomework;
    private Integer lateHomework;

    // Constructors
    public HomeworkSummaryResponse() {}

    public HomeworkSummaryResponse(Integer totalHomework, Integer pendingHomework) {
        this.totalHomework = totalHomework;
        this.pendingHomework = pendingHomework;
    }

    // Getters and Setters
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getByStatus() { return byStatus; }
    public void setByStatus(String byStatus) { this.byStatus = byStatus; }

    public Integer getTotalHomework() { return totalHomework; }
    public void setTotalHomework(Integer totalHomework) { this.totalHomework = totalHomework; }

    public Integer getPendingHomework() { return pendingHomework; }
    public void setPendingHomework(Integer pendingHomework) { this.pendingHomework = pendingHomework; }

    public Integer getSubmittedHomework() { return submittedHomework; }
    public void setSubmittedHomework(Integer submittedHomework) { this.submittedHomework = submittedHomework; }

    public Integer getLateHomework() { return lateHomework; }
    public void setLateHomework(Integer lateHomework) { this.lateHomework = lateHomework; }
}