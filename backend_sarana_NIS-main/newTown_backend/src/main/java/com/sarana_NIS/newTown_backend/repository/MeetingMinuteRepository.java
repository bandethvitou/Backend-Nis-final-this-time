package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.communication.MeetingMinute;
import com.sarana_NIS.newTown_backend.model.enums.MinutesStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MeetingMinuteRepository extends JpaRepository<MeetingMinute, Long> {

    @Query("SELECT mm FROM MeetingMinute mm WHERE mm.meeting.id = :meetingId")
    List<MeetingMinute> findByMeetingId(@Param("meetingId") Long meetingId);

    @Query("SELECT mm FROM MeetingMinute mm WHERE mm.status = :status")
    List<MeetingMinute> findByStatus(@Param("status") MinutesStatus status);

    @Query("SELECT mm FROM MeetingMinute mm WHERE mm.date = :date")
    List<MeetingMinute> findByDate(@Param("date") LocalDate date);

    @Query("SELECT mm FROM MeetingMinute mm WHERE mm.date BETWEEN :startDate AND :endDate")
    List<MeetingMinute> findByDateBetween(@Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);

    @Query("SELECT mm FROM MeetingMinute mm WHERE mm.meeting.id = :meetingId AND mm.status = 'FINAL'")
    List<MeetingMinute> findFinalMinutesByMeeting(@Param("meetingId") Long meetingId);

    // ❌ DELETE THIS METHOD - MeetingMinute doesn't have createdBy field
    // @Query("SELECT mm FROM MeetingMinute mm WHERE mm.createdBy = :createdBy")
    // List<MeetingMinute> findByCreatedBy(@Param("createdBy") Long createdBy);

    @Query("SELECT COUNT(mm) FROM MeetingMinute mm WHERE mm.status = 'DRAFT'")
    Long countDraftMinutes();

    @Query("SELECT mm FROM MeetingMinute mm WHERE mm.meeting.id = :meetingId ORDER BY mm.createdAt DESC")
    List<MeetingMinute> findLatestByMeetingId(@Param("meetingId") Long meetingId);

    @Query("SELECT mm FROM MeetingMinute mm WHERE mm.status = 'DRAFT' AND mm.createdByUser.id = :userId")
    List<MeetingMinute> findDraftByUser(@Param("userId") Long userId);
}