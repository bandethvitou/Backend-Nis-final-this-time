package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.counselling.CounsellingSession;
import com.sarana_NIS.newTown_backend.model.enums.SessionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CounsellingSessionRepository extends JpaRepository<CounsellingSession, Long> {

    // ✅ FIXED: Use counsellingCase.student instead of student
    @Query("SELECT cs FROM CounsellingSession cs WHERE cs.counsellingCase.student.id = :studentId")
    List<CounsellingSession> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT cs FROM CounsellingSession cs WHERE cs.counsellingCase.id = :caseId")
    List<CounsellingSession> findByCaseId(@Param("caseId") Long caseId);

    @Query("SELECT cs FROM CounsellingSession cs WHERE cs.counsellor.id = :counsellorId")
    List<CounsellingSession> findByCounsellorId(@Param("counsellorId") Long counsellorId);

    @Query("SELECT cs FROM CounsellingSession cs WHERE cs.sessionType = :sessionType")
    List<CounsellingSession> findBySessionType(@Param("sessionType") SessionType sessionType);

    @Query("SELECT cs FROM CounsellingSession cs WHERE cs.sessionDate BETWEEN :startDate AND :endDate")
    List<CounsellingSession> findBySessionDateBetween(@Param("startDate") LocalDate startDate,
                                                      @Param("endDate") LocalDate endDate);

    @Query("SELECT cs FROM CounsellingSession cs WHERE cs.followUpRequired = true AND cs.followUpDate < :date")
    List<CounsellingSession> findOverdueFollowUps(@Param("date") LocalDate date);

    @Query("SELECT COUNT(cs) FROM CounsellingSession cs WHERE cs.counsellor.id = :counsellorId AND cs.sessionDate = :date")
    Long countByCounsellorAndDate(@Param("counsellorId") Long counsellorId,
                                  @Param("date") LocalDate date);

    @Query("SELECT SUM(cs.durationMinutes) FROM CounsellingSession cs WHERE cs.counsellor.id = :counsellorId")
    Integer sumDurationByCounsellor(@Param("counsellorId") Long counsellorId);

    @Query("SELECT COUNT(cs) FROM CounsellingSession cs WHERE cs.counsellingCase.id = :caseId")
    Long countByCaseId(@Param("caseId") Long caseId);

    @Query("SELECT cs FROM CounsellingSession cs WHERE cs.counsellingCase.student.id = :studentId ORDER BY cs.sessionDate DESC")
    List<CounsellingSession> findLatestByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT COUNT(cs) FROM CounsellingSession cs WHERE cs.sessionDate >= :date")
    Long countSessionsSince(@Param("date") LocalDate date);
}