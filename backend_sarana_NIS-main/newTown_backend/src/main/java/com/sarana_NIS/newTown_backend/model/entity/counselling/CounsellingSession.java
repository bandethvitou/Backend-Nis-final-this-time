package com.sarana_NIS.newTown_backend.model.entity.counselling;

import com.sarana_NIS.newTown_backend.model.entity.BaseEntity;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.enums.SessionStatus;
import com.sarana_NIS.newTown_backend.model.enums.SessionType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "counselling_sessions")
public class CounsellingSession extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "case_id", nullable = false)
    private CounsellingCase counsellingCase;  // ✅ Student is accessed through this

    @Enumerated(EnumType.STRING)
    @Column(name = "session_type", nullable = false)
    private SessionType sessionType;

    @Column(name = "session_date", nullable = false)
    private LocalDateTime sessionDate;  // ✅ This is LocalDateTime

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SessionStatus status = SessionStatus.SCHEDULED;

    @ManyToOne
    @JoinColumn(name = "counsellor_id")
    private User counsellor;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;  // ✅ Use this, not duration

    @Column(name = "follow_up_required")
    private Boolean followUpRequired = false;

    @Column(name = "follow_up_date")
    private LocalDateTime followUpDate;  // ✅ This is LocalDateTime

    // ❌ NO direct student field
    // ❌ NO duration field (use durationMinutes)

    // Constructors
    public CounsellingSession() {
        this.status = SessionStatus.SCHEDULED;
        this.followUpRequired = false;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CounsellingCase getCounsellingCase() { return counsellingCase; }
    public void setCounsellingCase(CounsellingCase counsellingCase) { this.counsellingCase = counsellingCase; }

    public SessionType getSessionType() { return sessionType; }
    public void setSessionType(SessionType sessionType) { this.sessionType = sessionType; }

    public LocalDateTime getSessionDate() { return sessionDate; }
    public void setSessionDate(LocalDateTime sessionDate) { this.sessionDate = sessionDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public SessionStatus getStatus() { return status; }
    public void setStatus(SessionStatus status) { this.status = status; }

    public User getCounsellor() { return counsellor; }
    public void setCounsellor(User counsellor) { this.counsellor = counsellor; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public Boolean getFollowUpRequired() { return followUpRequired; }
    public void setFollowUpRequired(Boolean followUpRequired) { this.followUpRequired = followUpRequired; }

    public LocalDateTime getFollowUpDate() { return followUpDate; }
    public void setFollowUpDate(LocalDateTime followUpDate) { this.followUpDate = followUpDate; }

    // Helper methods
    public boolean isScheduled() { return this.status == SessionStatus.SCHEDULED; }
    public boolean isCompleted() { return this.status == SessionStatus.COMPLETED; }
    public boolean isCancelled() { return this.status == SessionStatus.CANCELLED; }

    public void complete() { this.status = SessionStatus.COMPLETED; }
    public void cancel() { this.status = SessionStatus.CANCELLED; }
}