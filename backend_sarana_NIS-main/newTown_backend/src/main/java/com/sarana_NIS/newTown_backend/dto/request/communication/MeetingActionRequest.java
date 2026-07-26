package com.sarana_NIS.newTown_backend.dto.request.communication;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class MeetingActionRequest {

    @NotNull(message = "Meeting ID is required")
    private Long meetingId;

    private String action;

    // For reschedule
    private LocalDate newDate;
    private LocalTime newTime;
    private String newLocation;
    private Integer newDuration;

    // Constructors
    public MeetingActionRequest() {}

    public MeetingActionRequest(Long meetingId, String action) {
        this.meetingId = meetingId;
        this.action = action;
    }

    // Getters and Setters
    public Long getMeetingId() { return meetingId; }
    public void setMeetingId(Long meetingId) { this.meetingId = meetingId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public LocalDate getNewDate() { return newDate; }
    public void setNewDate(LocalDate newDate) { this.newDate = newDate; }

    public LocalTime getNewTime() { return newTime; }
    public void setNewTime(LocalTime newTime) { this.newTime = newTime; }

    public String getNewLocation() { return newLocation; }
    public void setNewLocation(String newLocation) { this.newLocation = newLocation; }

    public Integer getNewDuration() { return newDuration; }
    public void setNewDuration(Integer newDuration) { this.newDuration = newDuration; }
}