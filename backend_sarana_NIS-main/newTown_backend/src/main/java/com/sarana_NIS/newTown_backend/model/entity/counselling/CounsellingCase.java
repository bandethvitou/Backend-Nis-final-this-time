package com.sarana_NIS.newTown_backend.model.entity.counselling;

import com.sarana_NIS.newTown_backend.model.entity.BaseEntity;
import com.sarana_NIS.newTown_backend.model.entity.core.User;  // ✅ ADD THIS IMPORT
import com.sarana_NIS.newTown_backend.model.entity.user.Student;
import com.sarana_NIS.newTown_backend.model.enums.CasePriority;
import com.sarana_NIS.newTown_backend.model.enums.CaseStatus;
import com.sarana_NIS.newTown_backend.model.enums.IssueSeverity;
import com.sarana_NIS.newTown_backend.model.enums.IssueType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "counselling_cases")
public class CounsellingCase extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Enumerated(EnumType.STRING)
    @Column(name = "issue_type", nullable = false)
    private IssueType issueType;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity")
    private IssueSeverity severity;  // ✅ Removed redundant initializer

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CaseStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private CasePriority priority;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "action_plan", columnDefinition = "TEXT")
    private String actionPlan;

    @ManyToOne
    @JoinColumn(name = "opened_by")
    private User openedBy;

    @Column(name = "opened_at")
    private LocalDateTime openedAt;

    @ManyToOne
    @JoinColumn(name = "closed_by")
    private User closedBy;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @Column(name = "closure_reason", columnDefinition = "TEXT")
    private String closureReason;

    @Column(name = "is_urgent")
    private Boolean isUrgent;

    @Column(name = "follow_up_required")
    private Boolean followUpRequired;

    @OneToMany(mappedBy = "counsellingCase", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CounsellingSession> sessions = new ArrayList<>();

    // Constructors
    public CounsellingCase() {
        this.openedAt = LocalDateTime.now();
        this.status = CaseStatus.ACTIVE;
        this.severity = IssueSeverity.MEDIUM;
        this.priority = CasePriority.MEDIUM;
        this.isUrgent = false;
        this.followUpRequired = false;
        this.sessions = new ArrayList<>();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public IssueType getIssueType() { return issueType; }
    public void setIssueType(IssueType issueType) { this.issueType = issueType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public IssueSeverity getSeverity() { return severity; }
    public void setSeverity(IssueSeverity severity) { this.severity = severity; }

    public CaseStatus getStatus() { return status; }
    public void setStatus(CaseStatus status) { this.status = status; }

    public CasePriority getPriority() { return priority; }
    public void setPriority(CasePriority priority) { this.priority = priority; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getActionPlan() { return actionPlan; }
    public void setActionPlan(String actionPlan) { this.actionPlan = actionPlan; }

    public User getOpenedBy() { return openedBy; }
    public void setOpenedBy(User openedBy) { this.openedBy = openedBy; }

    public LocalDateTime getOpenedAt() { return openedAt; }
    public void setOpenedAt(LocalDateTime openedAt) { this.openedAt = openedAt; }

    public User getClosedBy() { return closedBy; }
    public void setClosedBy(User closedBy) { this.closedBy = closedBy; }

    public LocalDateTime getClosedAt() { return closedAt; }
    public void setClosedAt(LocalDateTime closedAt) { this.closedAt = closedAt; }

    public String getClosureReason() { return closureReason; }
    public void setClosureReason(String closureReason) { this.closureReason = closureReason; }

    public Boolean getIsUrgent() { return isUrgent; }
    public void setIsUrgent(Boolean isUrgent) { this.isUrgent = isUrgent; }

    public Boolean getFollowUpRequired() { return followUpRequired; }
    public void setFollowUpRequired(Boolean followUpRequired) { this.followUpRequired = followUpRequired; }

    public List<CounsellingSession> getSessions() { return sessions; }
    public void setSessions(List<CounsellingSession> sessions) { this.sessions = sessions; }

    // Helper methods
    public void addSession(CounsellingSession session) {
        if (this.sessions == null) {
            this.sessions = new ArrayList<>();
        }
        this.sessions.add(session);
        session.setCounsellingCase(this);
    }

    public void removeSession(CounsellingSession session) {
        if (this.sessions != null) {
            this.sessions.remove(session);
            session.setCounsellingCase(null);
        }
    }

    public boolean isActive() {
        return this.status == CaseStatus.ACTIVE || this.status == CaseStatus.IN_PROGRESS;
    }

    public boolean isClosed() {
        return this.status == CaseStatus.CLOSED || this.status == CaseStatus.RESOLVED;
    }

    public boolean isResolved() {
        return this.status == CaseStatus.RESOLVED;
    }

    public void open() {
        this.status = CaseStatus.ACTIVE;
        this.openedAt = LocalDateTime.now();
    }

    public void close(String reason, User closedBy) {
        this.status = CaseStatus.CLOSED;
        this.closedAt = LocalDateTime.now();
        this.closedBy = closedBy;
        this.closureReason = reason;
    }

    public void resolve(String reason, User closedBy) {
        this.status = CaseStatus.RESOLVED;
        this.closedAt = LocalDateTime.now();
        this.closedBy = closedBy;
        this.closureReason = reason;
    }

    public void markInProgress() {
        this.status = CaseStatus.IN_PROGRESS;
    }

    public void markUrgent() {
        this.isUrgent = true;
        this.priority = CasePriority.HIGH;
    }
}