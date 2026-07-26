package com.sarana_NIS.newTown_backend.dto.response.academic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarana_NIS.newTown_backend.model.enums.HomeworkStatus;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomeworkSubmissionResponse {

    private Long id;
    private Long homeworkId;
    private String homeworkTitle;
    private Long studentId;
    private String studentName;
    private String studentClass;
    private String submissionUrl;
    private LocalDateTime submissionDate;
    private HomeworkStatus status;
    private Double score;
    private String feedback;
    private Boolean reviewedByParent;
    private Boolean reviewedByTeacher;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

    // Constructors
    public HomeworkSubmissionResponse() {}

    public HomeworkSubmissionResponse(Long id, Long homeworkId, Long studentId) {
        this.id = id;
        this.homeworkId = homeworkId;
        this.studentId = studentId;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getHomeworkId() { return homeworkId; }
    public void setHomeworkId(Long homeworkId) { this.homeworkId = homeworkId; }

    public String getHomeworkTitle() { return homeworkTitle; }
    public void setHomeworkTitle(String homeworkTitle) { this.homeworkTitle = homeworkTitle; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentClass() { return studentClass; }
    public void setStudentClass(String studentClass) { this.studentClass = studentClass; }

    public String getSubmissionUrl() { return submissionUrl; }
    public void setSubmissionUrl(String submissionUrl) { this.submissionUrl = submissionUrl; }

    public LocalDateTime getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(LocalDateTime submissionDate) { this.submissionDate = submissionDate; }

    public HomeworkStatus getStatus() { return status; }
    public void setStatus(HomeworkStatus status) { this.status = status; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

    public Boolean getReviewedByParent() { return reviewedByParent; }
    public void setReviewedByParent(Boolean reviewedByParent) { this.reviewedByParent = reviewedByParent; }

    public Boolean getReviewedByTeacher() { return reviewedByTeacher; }
    public void setReviewedByTeacher(Boolean reviewedByTeacher) { this.reviewedByTeacher = reviewedByTeacher; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
}