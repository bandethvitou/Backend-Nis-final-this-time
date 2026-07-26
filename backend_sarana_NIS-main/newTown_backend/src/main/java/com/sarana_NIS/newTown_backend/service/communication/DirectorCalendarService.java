package com.sarana_NIS.newTown_backend.service.communication;

import com.sarana_NIS.newTown_backend.model.entity.communication.DirectorCalendar;
import com.sarana_NIS.newTown_backend.model.enums.EventType;
import com.sarana_NIS.newTown_backend.repository.DirectorCalendarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class DirectorCalendarService {

    private static final Logger log = LoggerFactory.getLogger(DirectorCalendarService.class);

    private final DirectorCalendarRepository calendarRepository;

    public DirectorCalendarService(DirectorCalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    @Transactional
    public DirectorCalendar createEvent(DirectorCalendar event) {
        DirectorCalendar saved = calendarRepository.save(event);
        log.info("Calendar event created: {}", event.getTitle());
        return saved;
    }

    @Transactional
    public DirectorCalendar updateEvent(Long eventId, DirectorCalendar updates) {
        DirectorCalendar event = calendarRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (updates.getTitle() != null) event.setTitle(updates.getTitle());
        if (updates.getDescription() != null) event.setDescription(updates.getDescription());
        if (updates.getEventDate() != null) event.setEventDate(updates.getEventDate());
        if (updates.getStartTime() != null) event.setStartTime(updates.getStartTime());
        if (updates.getEndTime() != null) event.setEndTime(updates.getEndTime());
        if (updates.getEventType() != null) event.setEventType(updates.getEventType());
        if (updates.getLocation() != null) event.setLocation(updates.getLocation());
        if (updates.getAttendees() != null) event.setAttendees(updates.getAttendees());

        DirectorCalendar saved = calendarRepository.save(event);
        log.info("Calendar event updated: {}", eventId);
        return saved;
    }

    @Transactional
    public DirectorCalendar confirmEvent(Long eventId) {
        DirectorCalendar event = calendarRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        // ✅ FIXED: Use setIsConfirmed instead of setConfirmed
        event.setIsConfirmed(true);
        event.setConfirmedAt(java.time.LocalDateTime.now());
        DirectorCalendar saved = calendarRepository.save(event);
        log.info("Calendar event confirmed: {}", eventId);
        return saved;
    }

    public DirectorCalendar getEventById(Long eventId) {
        return calendarRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public List<DirectorCalendar> getEventsByDirector(Long directorId) {
        return calendarRepository.findByDirectorId(directorId);
    }

    public List<DirectorCalendar> getEventsByDirectorAndDate(Long directorId, LocalDate date) {
        return calendarRepository.findByDirectorAndDate(directorId, date);
    }

    public List<DirectorCalendar> getEventsByDirectorAndDateRange(Long directorId, LocalDate startDate, LocalDate endDate) {
        return calendarRepository.findByDirectorAndDateRange(directorId, startDate, endDate);
    }

    public List<DirectorCalendar> getEventsByDirectorAndType(Long directorId, EventType type) {
        return calendarRepository.findByDirectorAndEventType(directorId, type);
    }

    public List<DirectorCalendar> getUpcomingEvents(Long directorId) {
        return calendarRepository.findUpcomingEvents(directorId, LocalDate.now());
    }

    public List<DirectorCalendar> getHighPriorityEvents(Long directorId) {
        return calendarRepository.findHighPriorityEvents(directorId);
    }

    public List<DirectorCalendar> getUnconfirmedEvents(Long directorId) {
        return calendarRepository.findUnconfirmedEvents(directorId);
    }

    public List<DirectorCalendar> getRecurringEvents(Long directorId) {
        return calendarRepository.findRecurringEvents(directorId);
    }

    public long countEventsByDirectorAndDate(Long directorId, LocalDate date) {
        return calendarRepository.countByDirectorAndDate(directorId, date);
    }

    @Transactional
    public void deleteEvent(Long eventId) {
        DirectorCalendar event = calendarRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        calendarRepository.delete(event);
        log.info("Calendar event deleted: {}", eventId);
    }
}