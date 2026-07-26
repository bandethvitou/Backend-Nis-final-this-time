package com.sarana_NIS.newTown_backend.service.communication;

import com.sarana_NIS.newTown_backend.model.entity.communication.Meeting;
import com.sarana_NIS.newTown_backend.model.entity.communication.MeetingMinute;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.enums.MinutesStatus;
import com.sarana_NIS.newTown_backend.repository.MeetingMinuteRepository;
import com.sarana_NIS.newTown_backend.repository.MeetingRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MeetingMinuteService {

    private static final Logger log = LoggerFactory.getLogger(MeetingMinuteService.class);

    private final MeetingMinuteRepository minuteRepository;
    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    public MeetingMinuteService(MeetingMinuteRepository minuteRepository, MeetingRepository meetingRepository,
                                UserRepository userRepository) {
        this.minuteRepository = minuteRepository;
        this.meetingRepository = meetingRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public MeetingMinute createMinutes(MeetingMinute minutes, Long createdByUserId) {
        User createdBy = userRepository.findById(createdByUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (minutes.getMeeting() != null && minutes.getMeeting().getId() != null) {
            Meeting meeting = meetingRepository.findById(minutes.getMeeting().getId())
                    .orElseThrow(() -> new RuntimeException("Meeting not found"));
            minutes.setMeeting(meeting);
        }

        minutes.setStatus(MinutesStatus.DRAFT);
        // ✅ Fix: Use setCreatedByUser() instead of setCreatedBy()
        // setCreatedBy() is from BaseEntity and expects Long (ID)
        // setCreatedByUser() expects User object
        minutes.setCreatedByUser(createdBy);

        MeetingMinute saved = minuteRepository.save(minutes);
        log.info("Meeting minutes created: {}", saved.getTitle());
        return saved;
    }

    @Transactional
    public MeetingMinute finalizeMinutes(Long minuteId) {
        MeetingMinute minutes = minuteRepository.findById(minuteId)
                .orElseThrow(() -> new RuntimeException("Minutes not found"));
        minutes.setStatus(MinutesStatus.FINAL);
        MeetingMinute saved = minuteRepository.save(minutes);
        log.info("Meeting minutes finalized: {}", minuteId);
        return saved;
    }

    public MeetingMinute getMinutesById(Long minuteId) {
        return minuteRepository.findById(minuteId)
                .orElseThrow(() -> new RuntimeException("Minutes not found"));
    }

    public List<MeetingMinute> getMinutesByMeeting(Long meetingId) {
        return minuteRepository.findByMeetingId(meetingId);
    }

    public List<MeetingMinute> getMinutesByDate(LocalDate date) {
        return minuteRepository.findByDate(date);
    }

    public List<MeetingMinute> getMinutesByDateRange(LocalDate startDate, LocalDate endDate) {
        return minuteRepository.findByDateBetween(startDate, endDate);
    }

    public List<MeetingMinute> getMinutesByStatus(MinutesStatus status) {
        return minuteRepository.findByStatus(status);
    }

    public List<MeetingMinute> getFinalMinutesByMeeting(Long meetingId) {
        return minuteRepository.findFinalMinutesByMeeting(meetingId);
    }

    public List<MeetingMinute> getDraftMinutesByUser(Long userId) {
        return minuteRepository.findDraftByUser(userId);
    }

    @Transactional
    public MeetingMinute updateMinutes(Long minuteId, MeetingMinute updates) {
        MeetingMinute minutes = minuteRepository.findById(minuteId)
                .orElseThrow(() -> new RuntimeException("Minutes not found"));

        if (updates.getTitle() != null) minutes.setTitle(updates.getTitle());
        if (updates.getDate() != null) minutes.setDate(updates.getDate());
        if (updates.getAttendees() != null) minutes.setAttendees(updates.getAttendees());
        if (updates.getContent() != null) minutes.setContent(updates.getContent());

        MeetingMinute saved = minuteRepository.save(minutes);
        log.info("Meeting minutes updated: {}", minuteId);
        return saved;
    }

    @Transactional
    public void deleteMinutes(Long minuteId) {
        MeetingMinute minutes = minuteRepository.findById(minuteId)
                .orElseThrow(() -> new RuntimeException("Minutes not found"));
        minuteRepository.delete(minutes);
        log.info("Meeting minutes deleted: {}", minuteId);
    }
}