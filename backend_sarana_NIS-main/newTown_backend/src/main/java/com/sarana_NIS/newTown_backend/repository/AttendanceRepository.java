package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.academic.Attendance;
import com.sarana_NIS.newTown_backend.model.enums.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("SELECT a FROM Attendance a WHERE a.student.id = :studentId AND a.classSubject.id = :classSubjectId AND a.date = :date")
    Optional<Attendance> findByStudentClassSubjectDate(@Param("studentId") Long studentId,
                                                       @Param("classSubjectId") Long classSubjectId,
                                                       @Param("date") LocalDate date);

    @Query("SELECT a FROM Attendance a WHERE a.classSubject.id = :classSubjectId AND a.date = :date")
    List<Attendance> findByClassSubjectAndDate(@Param("classSubjectId") Long classSubjectId,
                                               @Param("date") LocalDate date);

    @Query("SELECT a FROM Attendance a WHERE a.student.id = :studentId AND a.date BETWEEN :startDate AND :endDate")
    List<Attendance> findByStudentAndDateRange(@Param("studentId") Long studentId,
                                               @Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);

    @Query("SELECT a FROM Attendance a WHERE a.classSubject.id = :classSubjectId AND a.date BETWEEN :startDate AND :endDate")
    List<Attendance> findByClassSubjectAndDateRange(@Param("classSubjectId") Long classSubjectId,
                                                    @Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.id = :studentId AND a.status = :status")
    Long countByStudentAndStatus(@Param("studentId") Long studentId,
                                 @Param("status") AttendanceStatus status);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.classSubject.id = :classSubjectId AND a.status = :status AND a.date = :date")
    Long countByClassSubjectStatusDate(@Param("classSubjectId") Long classSubjectId,
                                       @Param("status") AttendanceStatus status,
                                       @Param("date") LocalDate date);

    @Query("SELECT a FROM Attendance a WHERE a.student.id = :studentId AND a.status = 'ABSENT' AND a.date >= :date")
    List<Attendance> findRecentAbsences(@Param("studentId") Long studentId,
                                        @Param("date") LocalDate date);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.date = :date")
    Long countByDate(@Param("date") LocalDate date);

    @Query("SELECT a.student.id, COUNT(a) FROM Attendance a " +
            "WHERE a.classSubject.id = :classSubjectId AND a.status = 'ABSENT' " +
            "GROUP BY a.student.id ORDER BY COUNT(a) DESC")
    List<Object[]> findAbsentStudentsByClassSubject(@Param("classSubjectId") Long classSubjectId);
}