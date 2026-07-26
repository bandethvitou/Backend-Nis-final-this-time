package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.communication.DirectorCalendar;
import com.sarana_NIS.newTown_backend.model.enums.EventType;
import com.sarana_NIS.newTown_backend.model.enums.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DirectorCalendarRepository extends JpaRepository<DirectorCalendar, Long> {

    @Query("SELECT dc FROM DirectorCalendar dc WHERE dc.director.id = :directorId")
    List<DirectorCalendar> findByDirectorId(@Param("directorId") Long directorId);

    @Query("SELECT dc FROM DirectorCalendar dc WHERE dc.director.id = :directorId AND dc.eventDate = :date")
    List<DirectorCalendar> findByDirectorAndDate(@Param("directorId") Long directorId,
                                                 @Param("date") LocalDate date);

    @Query("SELECT dc FROM DirectorCalendar dc WHERE dc.director.id = :directorId AND dc.eventDate BETWEEN :startDate AND :endDate")
    List<DirectorCalendar> findByDirectorAndDateRange(@Param("directorId") Long directorId,
                                                      @Param("startDate") LocalDate startDate,
                                                      @Param("endDate") LocalDate endDate);

    @Query("SELECT dc FROM DirectorCalendar dc WHERE dc.director.id = :directorId AND dc.eventType = :eventType")
    List<DirectorCalendar> findByDirectorAndEventType(@Param("directorId") Long directorId,
                                                      @Param("eventType") EventType eventType);

    @Query("SELECT dc FROM DirectorCalendar dc WHERE dc.director.id = :directorId AND dc.isAllDay = true")
    List<DirectorCalendar> findAllDayEvents(@Param("directorId") Long directorId);

    @Query("SELECT dc FROM DirectorCalendar dc WHERE dc.director.id = :directorId AND dc.isConfirmed = false")
    List<DirectorCalendar> findUnconfirmedEvents(@Param("directorId") Long directorId);

    @Query("SELECT dc FROM DirectorCalendar dc WHERE dc.director.id = :directorId AND dc.isRecurring = true")
    List<DirectorCalendar> findRecurringEvents(@Param("directorId") Long directorId);

    @Query("SELECT dc FROM DirectorCalendar dc WHERE dc.director.id = :directorId AND dc.eventDate >= :date ORDER BY dc.eventDate ASC")
    List<DirectorCalendar> findUpcomingEvents(@Param("directorId") Long directorId,
                                              @Param("date") LocalDate date);

    @Query("SELECT dc FROM DirectorCalendar dc WHERE dc.director.id = :directorId AND dc.priority = 'HIGH'")
    List<DirectorCalendar> findHighPriorityEvents(@Param("directorId") Long directorId);

    @Query("SELECT COUNT(dc) FROM DirectorCalendar dc WHERE dc.director.id = :directorId AND dc.eventDate = :date")
    Long countByDirectorAndDate(@Param("directorId") Long directorId,
                                @Param("date") LocalDate date);

    @Query("SELECT dc FROM DirectorCalendar dc WHERE dc.director.id = :directorId AND dc.eventDate = :date AND dc.isAllDay = false")
    List<DirectorCalendar> findTimedEventsByDate(@Param("directorId") Long directorId,
                                                 @Param("date") LocalDate date);
}