package com.sarana_NIS.newTown_backend.model.entity.academic;

import com.sarana_NIS.newTown_backend.model.entity.BaseEntity;
import com.sarana_NIS.newTown_backend.model.entity.user.Student;
import com.sarana_NIS.newTown_backend.model.enums.HomeworkStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "homework_submissions")
public class HomeworkSubmission extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "homework_id", nullable = false)
    private Homework homework;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "submission_url")
    private String submissionUrl;

    @Column(name = "submission_date")
    private LocalDateTime submissionDate;

    @Enumerated(EnumType.STRING)
    private HomeworkStatus status = HomeworkStatus.PENDING;

    private Double score;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    @Column(name = "reviewed_by_parent")
    private Boolean reviewedByParent = false;

    @Column(name = "reviewed_by_teacher")
    private Boolean reviewedByTeacher = false;

    // Constructors
    public HomeworkSubmission() {}

    public HomeworkSubmission(Homework homework, Student student) {
        this.homework = homework;
        this.student = student;
    }

    // Getters and Setters
    public Homework getHomework() { return homework; }
    public void setHomework(Homework homework) { this.homework = homework; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

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
}