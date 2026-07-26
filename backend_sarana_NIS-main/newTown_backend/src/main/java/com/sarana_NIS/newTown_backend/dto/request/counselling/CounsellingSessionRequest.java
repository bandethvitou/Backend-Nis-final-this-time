package com.sarana_NIS.newTown_backend.dto.request.counselling;

import com.sarana_NIS.newTown_backend.model.enums.SessionType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class CounsellingSessionRequest {

    @NotNull(message = "Case ID is required")
    private Long caseId;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Session date is required")
    private LocalDate sessionDate;  // ✅ This is LocalDate (request)

    @NotNull(message = "Duration is required")
    private Integer duration;

    @NotNull(message = "Session type is required")
    private SessionType sessionType;

    private String notes;
    private Boolean followUpRequired;
    private LocalDate followUpDate;  // ✅ This is LocalDate (request)
    private Long counsellorId;

    // Constructors
    public CounsellingSessionRequest() {}

    // Getters and Setters
    public Long getCaseId() { return caseId; }
    public void setCaseId(Long caseId) { this.caseId = caseId; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public LocalDate getSessionDate() { return sessionDate; }
    public void setSessionDate(LocalDate sessionDate) { this.sessionDate = sessionDate; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public SessionType getSessionType() { return sessionType; }
    public void setSessionType(SessionType sessionType) { this.sessionType = sessionType; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Boolean getFollowUpRequired() { return followUpRequired; }
    public void setFollowUpRequired(Boolean followUpRequired) { this.followUpRequired = followUpRequired; }

    public LocalDate getFollowUpDate() { return followUpDate; }
    public void setFollowUpDate(LocalDate followUpDate) { this.followUpDate = followUpDate; }

    public Long getCounsellorId() { return counsellorId; }
    public void setCounsellorId(Long counsellorId) { this.counsellorId = counsellorId; }
}