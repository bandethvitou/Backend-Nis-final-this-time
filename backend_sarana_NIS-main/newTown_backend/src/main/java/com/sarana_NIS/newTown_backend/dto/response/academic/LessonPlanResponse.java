package com.sarana_NIS.newTown_backend.dto.response.academic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarana_NIS.newTown_backend.model.enums.LessonPlanStatus;
import com.sarana_NIS.newTown_backend.model.enums.Shift;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonPlanResponse {

    private Long id;

    // Class Subject Info
    private Long classSubjectId;
    private String classSubjectName;
    private String className;
    private String subjectName;
    private String teacherName;

    // Lesson Details
    private LocalDate date;
    private Shift shift;
    private String topic;
    private String objective;
    private String activities;
    private Boolean homeworkAssigned;

    // Status
    private LessonPlanStatus status;

    // Submitted By
    private String submittedBy;
    private Long submittedById;
    private String submittedByName;

    // Approved By
    private String approvedBy;
    private Long approvedById;
    private String approvedByName;

    private LocalDateTime submittedAt;
    private LocalDateTime approvedAt;
    private String revisionComment;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public LessonPlanResponse() {}

    public LessonPlanResponse(Long id, String topic) {
        this.id = id;
        this.topic = topic;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClassSubjectId() { return classSubjectId; }
    public void setClassSubjectId(Long classSubjectId) { this.classSubjectId = classSubjectId; }

    public String getClassSubjectName() { return classSubjectName; }
    public void setClassSubjectName(String classSubjectName) { this.classSubjectName = classSubjectName; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Shift getShift() { return shift; }
    public void setShift(Shift shift) { this.shift = shift; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getObjective() { return objective; }
    public void setObjective(String objective) { this.objective = objective; }

    public String getActivities() { return activities; }
    public void setActivities(String activities) { this.activities = activities; }

    public Boolean getHomeworkAssigned() { return homeworkAssigned; }
    public void setHomeworkAssigned(Boolean homeworkAssigned) { this.homeworkAssigned = homeworkAssigned; }

    public LessonPlanStatus getStatus() { return status; }
    public void setStatus(LessonPlanStatus status) { this.status = status; }

    public String getSubmittedBy() { return submittedBy; }
    public void setSubmittedBy(String submittedBy) { this.submittedBy = submittedBy; }

    public Long getSubmittedById() { return submittedById; }
    public void setSubmittedById(Long submittedById) { this.submittedById = submittedById; }

    public String getSubmittedByName() { return submittedByName; }
    public void setSubmittedByName(String submittedByName) { this.submittedByName = submittedByName; }

    public String getApprovedBy() { return approvedBy; }
    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }

    public Long getApprovedById() { return approvedById; }
    public void setApprovedById(Long approvedById) { this.approvedById = approvedById; }

    public String getApprovedByName() { return approvedByName; }
    public void setApprovedByName(String approvedByName) { this.approvedByName = approvedByName; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }

    public LocalDateTime getApprovedAt() { return approvedAt; }
    public void setApprovedAt(LocalDateTime approvedAt) { this.approvedAt = approvedAt; }

    public String getRevisionComment() { return revisionComment; }
    public void setRevisionComment(String revisionComment) { this.revisionComment = revisionComment; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}