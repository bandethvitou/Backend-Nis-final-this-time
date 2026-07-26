package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.communication.Meeting;
import com.sarana_NIS.newTown_backend.model.enums.MeetingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query("SELECT m FROM Meeting m WHERE m.host.id = :hostId")
    List<Meeting> findByHostId(@Param("hostId") Long hostId);

    @Query("SELECT m FROM Meeting m WHERE m.status = :status")
    List<Meeting> findByStatus(@Param("status") MeetingStatus status);

    @Query("SELECT m FROM Meeting m WHERE m.meetingDate = :date")
    List<Meeting> findByMeetingDate(@Param("date") LocalDate date);

    @Query("SELECT m FROM Meeting m WHERE m.meetingDate BETWEEN :startDate AND :endDate")
    List<Meeting> findByMeetingDateBetween(@Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);

    @Query("SELECT m FROM Meeting m WHERE m.meetingDate >= :date AND m.status = 'SCHEDULED'")
    List<Meeting> findUpcomingMeetings(@Param("date") LocalDate date);

    @Query("SELECT m FROM Meeting m WHERE m.meetingDate < :date AND m.status = 'SCHEDULED'")
    List<Meeting> findPastScheduledMeetings(@Param("date") LocalDate date);

    @Query("SELECT m FROM Meeting m WHERE m.meetingDate = :date AND m.status = 'SCHEDULED'")
    List<Meeting> findScheduledMeetingsForDate(@Param("date") LocalDate date);

    @Query("SELECT COUNT(m) FROM Meeting m WHERE m.meetingDate = :date")
    Long countByMeetingDate(@Param("date") LocalDate date);

    @Query("SELECT m FROM Meeting m WHERE m.meetingDate >= :date ORDER BY m.meetingDate ASC")
    List<Meeting> findUpcomingMeetingsOrdered(@Param("date") LocalDate date);

    @Query("SELECT m FROM Meeting m WHERE m.status = 'SCHEDULED' AND m.meetingDate <= :date")
    List<Meeting> findMeetingsNeedingCompletion(@Param("date") LocalDate date);

    @Query("SELECT COUNT(m) FROM Meeting m WHERE m.status = 'SCHEDULED'")
    Long countScheduledMeetings();

    @Query("SELECT m FROM Meeting m WHERE m.meetingDate BETWEEN :start AND :end AND m.status = 'SCHEDULED'")
    List<Meeting> findScheduledBetween(@Param("start") LocalDate start,
                                       @Param("end") LocalDate end);
}