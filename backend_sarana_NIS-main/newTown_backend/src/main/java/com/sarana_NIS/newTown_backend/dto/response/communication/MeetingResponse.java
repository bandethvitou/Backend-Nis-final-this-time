package com.sarana_NIS.newTown_backend.dto.response.communication;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDate meetingDate;
    private LocalTime meetingTime;
    private Integer duration;
    private String location;
    private String status;
    private String statusColor;

    private Long hostId;
    private String hostName;

    private List<Long> attendeeIds = new ArrayList<>();
    private List<String> attendeeNames = new ArrayList<>();

    private Boolean isUpcoming;
    private Integer daysUntil;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public MeetingResponse() {}

    public MeetingResponse(Long id, String title, LocalDate meetingDate) {
        this.id = id;
        this.title = title;
        this.meetingDate = meetingDate;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getMeetingDate() { return meetingDate; }
    public void setMeetingDate(LocalDate meetingDate) { this.meetingDate = meetingDate; }

    public LocalTime getMeetingTime() { return meetingTime; }
    public void setMeetingTime(LocalTime meetingTime) { this.meetingTime = meetingTime; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getStatusColor() { return statusColor; }
    public void setStatusColor(String statusColor) { this.statusColor = statusColor; }

    public Long getHostId() { return hostId; }
    public void setHostId(Long hostId) { this.hostId = hostId; }

    public String getHostName() { return hostName; }
    public void setHostName(String hostName) { this.hostName = hostName; }

    public List<Long> getAttendeeIds() { return attendeeIds; }
    public void setAttendeeIds(List<Long> attendeeIds) { this.attendeeIds = attendeeIds; }

    public List<String> getAttendeeNames() { return attendeeNames; }
    public void setAttendeeNames(List<String> attendeeNames) { this.attendeeNames = attendeeNames; }

    public Boolean getIsUpcoming() { return isUpcoming; }
    public void setIsUpcoming(Boolean isUpcoming) { this.isUpcoming = isUpcoming; }

    public Integer getDaysUntil() { return daysUntil; }
    public void setDaysUntil(Integer daysUntil) { this.daysUntil = daysUntil; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}