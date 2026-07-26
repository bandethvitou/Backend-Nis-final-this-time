package com.sarana_NIS.newTown_backend.dto.request.academic;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class HomeworkSubmissionRequest {

    @NotNull(message = "Homework ID is required")
    private Long homeworkId;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @Size(max = 255, message = "Submission URL must be less than 255 characters")
    private String submissionUrl;

    @Min(value = 0, message = "Score must be between 0 and 100")
    @Max(value = 100, message = "Score must be between 0 and 100")
    private Double score;

    @Size(max = 500, message = "Feedback must be less than 500 characters")
    private String feedback;

    private Boolean reviewedByParent;

    // Constructors
    public HomeworkSubmissionRequest() {}

    public HomeworkSubmissionRequest(Long homeworkId, Long studentId) {
        this.homeworkId = homeworkId;
        this.studentId = studentId;
    }

    // Getters and Setters
    public Long getHomeworkId() { return homeworkId; }
    public void setHomeworkId(Long homeworkId) { this.homeworkId = homeworkId; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getSubmissionUrl() { return submissionUrl; }
    public void setSubmissionUrl(String submissionUrl) { this.submissionUrl = submissionUrl; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

    public Boolean getReviewedByParent() { return reviewedByParent; }
    public void setReviewedByParent(Boolean reviewedByParent) { this.reviewedByParent = reviewedByParent; }
}