package com.sarana_NIS.newTown_backend.dto.response.communication;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingAttendeeResponse {

    private Long meetingId;
    private String meetingTitle;
    private Integer totalAttendees;
    private Integer confirmedAttendees;
    private Integer pendingAttendees;
    private Integer declinedAttendees;
    private List<MeetingSummaryResponse.AttendeeInfo> attendees = new ArrayList<>();

    // Constructors
    public MeetingAttendeeResponse() {}

    public MeetingAttendeeResponse(Long meetingId, String meetingTitle) {
        this.meetingId = meetingId;
        this.meetingTitle = meetingTitle;
    }

    // Getters and Setters
    public Long getMeetingId() { return meetingId; }
    public void setMeetingId(Long meetingId) { this.meetingId = meetingId; }

    public String getMeetingTitle() { return meetingTitle; }
    public void setMeetingTitle(String meetingTitle) { this.meetingTitle = meetingTitle; }

    public Integer getTotalAttendees() { return totalAttendees; }
    public void setTotalAttendees(Integer totalAttendees) { this.totalAttendees = totalAttendees; }

    public Integer getConfirmedAttendees() { return confirmedAttendees; }
    public void setConfirmedAttendees(Integer confirmedAttendees) { this.confirmedAttendees = confirmedAttendees; }

    public Integer getPendingAttendees() { return pendingAttendees; }
    public void setPendingAttendees(Integer pendingAttendees) { this.pendingAttendees = pendingAttendees; }

    public Integer getDeclinedAttendees() { return declinedAttendees; }
    public void setDeclinedAttendees(Integer declinedAttendees) { this.declinedAttendees = declinedAttendees; }

    public List<MeetingSummaryResponse.AttendeeInfo> getAttendees() { return attendees; }
    public void setAttendees(List<MeetingSummaryResponse.AttendeeInfo> attendees) { this.attendees = attendees; }
}