package com.sarana_NIS.newTown_backend.dto.response.communication;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingSummaryResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDate meetingDate;
    private LocalTime meetingTime;  // ✅ LocalTime
    private Integer duration;       // ✅ Duration in minutes
    private String location;
    private String status;
    private String host;
    private String hostName;
    private Integer attendeeCount;
    private List<AttendeeInfo> attendees = new ArrayList<>();

    // Constructors
    public MeetingSummaryResponse() {}

    public MeetingSummaryResponse(Long id, String title, LocalDate meetingDate) {
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

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public String getHostName() { return hostName; }
    public void setHostName(String hostName) { this.hostName = hostName; }

    public Integer getAttendeeCount() { return attendeeCount; }
    public void setAttendeeCount(Integer attendeeCount) { this.attendeeCount = attendeeCount; }

    public List<AttendeeInfo> getAttendees() { return attendees; }
    public void setAttendees(List<AttendeeInfo> attendees) { this.attendees = attendees; }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AttendeeInfo {
        private Long userId;
        private String name;
        private String status;

        public AttendeeInfo() {}

        public AttendeeInfo(Long userId, String name) {
            this.userId = userId;
            this.name = name;
        }

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}