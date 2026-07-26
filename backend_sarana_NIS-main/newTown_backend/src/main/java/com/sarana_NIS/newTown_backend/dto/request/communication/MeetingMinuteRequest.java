package com.sarana_NIS.newTown_backend.dto.request.communication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class MeetingMinuteRequest {

    private Long meetingId;

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 200, message = "Title must be between 2 and 200 characters")
    private String title;

    @NotNull(message = "Date is required")
    private LocalDate date;

    private String attendees;

    @NotBlank(message = "Content is required")
    @Size(min = 10, max = 5000, message = "Content must be between 10 and 5000 characters")
    private String content;

    // Constructors
    public MeetingMinuteRequest() {}

    public MeetingMinuteRequest(String title, LocalDate date, String content) {
        this.title = title;
        this.date = date;
        this.content = content;
    }

    // Getters and Setters
    public Long getMeetingId() { return meetingId; }
    public void setMeetingId(Long meetingId) { this.meetingId = meetingId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getAttendees() { return attendees; }
    public void setAttendees(String attendees) { this.attendees = attendees; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}