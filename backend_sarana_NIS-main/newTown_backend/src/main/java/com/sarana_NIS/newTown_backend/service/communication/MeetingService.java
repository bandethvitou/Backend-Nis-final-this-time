package com.sarana_NIS.newTown_backend.service.communication;

import com.sarana_NIS.newTown_backend.dto.request.communication.MeetingActionRequest;
import com.sarana_NIS.newTown_backend.dto.response.communication.MeetingSummaryResponse;
import com.sarana_NIS.newTown_backend.dto.response.communication.MeetingAttendeeResponse;
import com.sarana_NIS.newTown_backend.model.entity.communication.Meeting;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.enums.MeetingStatus;
import com.sarana_NIS.newTown_backend.repository.MeetingRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
public class MeetingService {

    private static final Logger log = LoggerFactory.getLogger(MeetingService.class);

    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    public MeetingService(MeetingRepository meetingRepository, UserRepository userRepository) {
        this.meetingRepository = meetingRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Meeting createMeeting(Meeting meeting, Long hostId) {
        User host = userRepository.findById(hostId)
                .orElseThrow(() -> new RuntimeException("Host not found"));

        meeting.setHost(host);
        meeting.setStatus(MeetingStatus.SCHEDULED);

        Meeting saved = meetingRepository.save(meeting);
        log.info("Meeting created: {}", meeting.getTitle());
        return saved;
    }

    @Transactional
    public Meeting completeMeeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        meeting.setStatus(MeetingStatus.COMPLETED);
        Meeting saved = meetingRepository.save(meeting);
        log.info("Meeting completed: {}", meetingId);
        return saved;
    }

    @Transactional
    public Meeting cancelMeeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        meeting.setStatus(MeetingStatus.CANCELLED);
        Meeting saved = meetingRepository.save(meeting);
        log.info("Meeting cancelled: {}", meetingId);
        return saved;
    }

    @Transactional
    public Meeting rescheduleMeeting(MeetingActionRequest request) {
        Meeting meeting = meetingRepository.findById(request.getMeetingId())
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        if (request.getNewDate() != null) {
            meeting.setMeetingDate(request.getNewDate());
        }
        // ✅ FIXED: Use setStartTime instead of setMeetingTime
        if (request.getNewTime() != null) {
            meeting.setStartTime(request.getNewTime());
        }
        if (request.getNewLocation() != null) {
            meeting.setLocation(request.getNewLocation());
        }

        Meeting saved = meetingRepository.save(meeting);
        log.info("Meeting rescheduled: {}", meeting.getId());
        return saved;
    }

    public Meeting getMeetingById(Long meetingId) {
        return meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
    }

    public List<Meeting> getMeetingsByHost(Long hostId) {
        return meetingRepository.findByHostId(hostId);
    }

    public List<Meeting> getMeetingsByStatus(MeetingStatus status) {
        return meetingRepository.findByStatus(status);
    }

    public List<Meeting> getMeetingsByDate(LocalDate date) {
        return meetingRepository.findByMeetingDate(date);
    }

    public List<Meeting> getMeetingsByDateRange(LocalDate startDate, LocalDate endDate) {
        return meetingRepository.findByMeetingDateBetween(startDate, endDate);
    }

    public List<Meeting> getUpcomingMeetings() {
        return meetingRepository.findUpcomingMeetings(LocalDate.now());
    }

    public List<Meeting> getScheduledMeetingsForDate(LocalDate date) {
        return meetingRepository.findScheduledMeetingsForDate(date);
    }

    public MeetingSummaryResponse getMeetingSummary(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        MeetingSummaryResponse response = new MeetingSummaryResponse();
        response.setId(meeting.getId());
        response.setTitle(meeting.getTitle());
        response.setDescription(meeting.getDescription());
        response.setMeetingDate(meeting.getMeetingDate());
        response.setMeetingTime(meeting.getStartTime());
        response.setDuration(calculateDuration(meeting));
        response.setLocation(meeting.getLocation());
        response.setStatus(meeting.getStatus().name());
        response.setHost(meeting.getHost() != null ? meeting.getHost().getUsername() : null);
        response.setHostName(meeting.getHost() != null ? meeting.getHost().getFullName() : null);

        return response;
    }

    public MeetingAttendeeResponse getMeetingAttendees(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        MeetingAttendeeResponse response = new MeetingAttendeeResponse();
        response.setMeetingId(meeting.getId());
        response.setMeetingTitle(meeting.getTitle());

        return response;
    }

    public long countScheduledMeetings() {
        return meetingRepository.countScheduledMeetings();
    }

    public long countMeetingsByDate(LocalDate date) {
        return meetingRepository.countByMeetingDate(date);
    }

    @Transactional
    public void deleteMeeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        meetingRepository.delete(meeting);
        log.info("Meeting deleted: {}", meetingId);
    }

    // ========== Helper Methods ==========

    private Integer calculateDuration(Meeting meeting) {
        if (meeting == null || meeting.getStartTime() == null || meeting.getEndTime() == null) {
            return 0;
        }
        return (int) Duration.between(meeting.getStartTime(), meeting.getEndTime()).toMinutes();
    }
}