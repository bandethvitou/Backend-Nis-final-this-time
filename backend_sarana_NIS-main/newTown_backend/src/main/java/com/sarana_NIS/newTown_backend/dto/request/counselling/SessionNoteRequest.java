package com.sarana_NIS.newTown_backend.dto.request.counselling;

import com.sarana_NIS.newTown_backend.model.enums.SessionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class SessionNoteRequest {

    @NotNull(message = "Case ID is required")
    private Long caseId;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Session date is required")
    private LocalDate sessionDate;

    @NotNull(message = "Duration is required")
    private Integer duration;

    @NotNull(message = "Session type is required")
    private SessionType sessionType;

    @NotBlank(message = "Notes are required")
    @Size(min = 10, max = 2000, message = "Notes must be between 10 and 2000 characters")
    private String notes;

    private Boolean followUpRequired = false;
    private LocalDate followUpDate;
    private Boolean parentInvolved = false;
    private String parentName;

    // Constructors
    public SessionNoteRequest() {}

    public SessionNoteRequest(Long caseId, Long studentId, LocalDate sessionDate, SessionType sessionType, String notes) {
        this.caseId = caseId;
        this.studentId = studentId;
        this.sessionDate = sessionDate;
        this.sessionType = sessionType;
        this.notes = notes;
    }

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

    public Boolean getParentInvolved() { return parentInvolved; }
    public void setParentInvolved(Boolean parentInvolved) { this.parentInvolved = parentInvolved; }

    public String getParentName() { return parentName; }
    public void setParentName(String parentName) { this.parentName = parentName; }
}