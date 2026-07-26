package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.academic.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {

    @Query("SELECT h FROM Homework h WHERE h.classSubject.id = :classSubjectId")
    List<Homework> findByClassSubjectId(@Param("classSubjectId") Long classSubjectId);

    @Query("SELECT h FROM Homework h WHERE h.classSubject.id = :classSubjectId AND h.dueDate >= CURRENT_DATE")
    List<Homework> findUpcomingByClassSubject(@Param("classSubjectId") Long classSubjectId);

    @Query("SELECT h FROM Homework h WHERE h.classSubject.id = :classSubjectId AND h.dueDate < CURRENT_DATE")
    List<Homework> findOverdueByClassSubject(@Param("classSubjectId") Long classSubjectId);

    @Query("SELECT h FROM Homework h WHERE h.assignedBy.id = :teacherId")
    List<Homework> findByTeacherId(@Param("teacherId") Long teacherId);

    @Query("SELECT h FROM Homework h WHERE h.dueDate BETWEEN :startDate AND :endDate")
    List<Homework> findByDueDateBetween(@Param("startDate") LocalDate startDate,
                                        @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(hs) FROM HomeworkSubmission hs WHERE hs.homework.id = :homeworkId")
    Long countSubmissions(@Param("homeworkId") Long homeworkId);

    @Query("SELECT h FROM Homework h WHERE h.classSubject.id = :classSubjectId AND h.status = 'PENDING'")
    List<Homework> findPendingByClassSubject(@Param("classSubjectId") Long classSubjectId);
}