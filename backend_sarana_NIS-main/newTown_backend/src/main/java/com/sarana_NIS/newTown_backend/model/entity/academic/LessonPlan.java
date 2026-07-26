package com.sarana_NIS.newTown_backend.model.entity.academic;

import com.sarana_NIS.newTown_backend.model.entity.BaseEntity;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.enums.LessonPlanStatus;
import com.sarana_NIS.newTown_backend.model.enums.Shift;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "lesson_plans")
public class LessonPlan extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "class_subject_id", nullable = false)
    private ClassSubject classSubject;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Shift shift;

    @Column(nullable = false, length = 200)
    private String topic;

    @Column(columnDefinition = "TEXT")
    private String objective;

    @Column(columnDefinition = "JSONB")
    private String activities;

    @Column(name = "homework_assigned")
    private Boolean homeworkAssigned = false;

    @Enumerated(EnumType.STRING)
    private LessonPlanStatus status = LessonPlanStatus.DRAFT;

    @ManyToOne
    @JoinColumn(name = "submitted_by")
    private User submittedBy;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "revision_comment", columnDefinition = "TEXT")
    private String revisionComment;

    // Constructors
    public LessonPlan() {}

    public LessonPlan(ClassSubject classSubject, LocalDate date, String topic) {
        this.classSubject = classSubject;
        this.date = date;
        this.topic = topic;
    }

    // Getters and Setters
    public ClassSubject getClassSubject() { return classSubject; }
    public void setClassSubject(ClassSubject classSubject) { this.classSubject = classSubject; }

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

    public User getSubmittedBy() { return submittedBy; }
    public void setSubmittedBy(User submittedBy) { this.submittedBy = submittedBy; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }

    public User getApprovedBy() { return approvedBy; }
    public void setApprovedBy(User approvedBy) { this.approvedBy = approvedBy; }

    public LocalDateTime getApprovedAt() { return approvedAt; }
    public void setApprovedAt(LocalDateTime approvedAt) { this.approvedAt = approvedAt; }

    public String getRevisionComment() { return revisionComment; }
    public void setRevisionComment(String revisionComment) { this.revisionComment = revisionComment; }
}