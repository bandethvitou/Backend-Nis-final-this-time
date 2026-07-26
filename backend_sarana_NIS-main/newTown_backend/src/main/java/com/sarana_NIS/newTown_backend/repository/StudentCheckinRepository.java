package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.admissions.StudentCheckin;
import com.sarana_NIS.newTown_backend.model.enums.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentCheckinRepository extends JpaRepository<StudentCheckin, Long> {

    @Query("SELECT sc FROM StudentCheckin sc WHERE sc.student.id = :studentId AND sc.checkinDate = :date")
    Optional<StudentCheckin> findByStudentAndDate(@Param("studentId") Long studentId,
                                                  @Param("date") LocalDate date);

    @Query("SELECT sc FROM StudentCheckin sc WHERE sc.classEntity.id = :classId AND sc.checkinDate = :date")
    List<StudentCheckin> findByClassAndDate(@Param("classId") Long classId,
                                            @Param("date") LocalDate date);

    @Query("SELECT sc FROM StudentCheckin sc WHERE sc.classEntity.id = :classId AND sc.checkinDate BETWEEN :startDate AND :endDate")
    List<StudentCheckin> findByClassAndDateRange(@Param("classId") Long classId,
                                                 @Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate);

    @Query("SELECT sc FROM StudentCheckin sc WHERE sc.student.id = :studentId AND sc.checkinDate BETWEEN :startDate AND :endDate")
    List<StudentCheckin> findByStudentAndDateRange(@Param("studentId") Long studentId,
                                                   @Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate);

    @Query("SELECT sc FROM StudentCheckin sc WHERE sc.status = :status AND sc.checkinDate = :date")
    List<StudentCheckin> findByStatusAndDate(@Param("status") AttendanceStatus status,
                                             @Param("date") LocalDate date);

    @Query("SELECT COUNT(sc) FROM StudentCheckin sc WHERE sc.classEntity.id = :classId AND sc.checkinDate = :date AND sc.status = :status")
    Long countByClassDateStatus(@Param("classId") Long classId,
                                @Param("date") LocalDate date,
                                @Param("status") AttendanceStatus status);

    @Query("SELECT sc FROM StudentCheckin sc WHERE sc.checkinTime < :time AND sc.checkinDate = :date")
    List<StudentCheckin> findEarlyCheckins(@Param("time") LocalTime time,
                                           @Param("date") LocalDate date);

    @Query("SELECT sc FROM StudentCheckin sc WHERE sc.checkinTime > :time AND sc.checkinDate = :date")
    List<StudentCheckin> findLateCheckins(@Param("time") LocalTime time,
                                          @Param("date") LocalDate date);

    @Query("SELECT sc.student.id, COUNT(sc) FROM StudentCheckin sc " +
            "WHERE sc.classEntity.id = :classId AND sc.status = 'ABSENT' " +
            "AND sc.checkinDate BETWEEN :startDate AND :endDate GROUP BY sc.student.id")
    List<Object[]> findAbsentCountByClassBetween(@Param("classId") Long classId,
                                                 @Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(sc) FROM StudentCheckin sc WHERE sc.isBulkAction = true AND sc.bulkActionId = :bulkId")
    Long countByBulkActionId(@Param("bulkId") String bulkId);

    @Query("SELECT sc FROM StudentCheckin sc WHERE sc.checkinDate = :date")
    List<StudentCheckin> findByDate(@Param("date") LocalDate date);

    @Query("SELECT COUNT(sc) FROM StudentCheckin sc WHERE sc.checkinDate = :date")
    Long countByDate(@Param("date") LocalDate date);

    @Query("SELECT AVG(sc.lateMinutes) FROM StudentCheckin sc WHERE sc.checkinDate = :date")
    Double findAverageLateMinutes(@Param("date") LocalDate date);
}