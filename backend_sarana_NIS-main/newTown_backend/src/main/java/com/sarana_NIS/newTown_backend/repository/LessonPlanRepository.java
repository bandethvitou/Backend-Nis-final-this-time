package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.academic.LessonPlan;
import com.sarana_NIS.newTown_backend.model.enums.LessonPlanStatus;
import com.sarana_NIS.newTown_backend.model.enums.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LessonPlanRepository extends JpaRepository<LessonPlan, Long> {

    @Query("SELECT lp FROM LessonPlan lp WHERE lp.classSubject.id = :classSubjectId")
    List<LessonPlan> findByClassSubjectId(@Param("classSubjectId") Long classSubjectId);

    @Query("SELECT lp FROM LessonPlan lp WHERE lp.classSubject.id = :classSubjectId AND lp.date = :date")
    List<LessonPlan> findByClassSubjectAndDate(@Param("classSubjectId") Long classSubjectId,
                                               @Param("date") LocalDate date);

    @Query("SELECT lp FROM LessonPlan lp WHERE lp.classSubject.id = :classSubjectId AND lp.date BETWEEN :startDate AND :endDate")
    List<LessonPlan> findByClassSubjectAndDateRange(@Param("classSubjectId") Long classSubjectId,
                                                    @Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate);

    @Query("SELECT lp FROM LessonPlan lp WHERE lp.status = :status")
    List<LessonPlan> findByStatus(@Param("status") LessonPlanStatus status);

    @Query("SELECT lp FROM LessonPlan lp WHERE lp.submittedBy.id = :teacherId")
    List<LessonPlan> findByTeacherId(@Param("teacherId") Long teacherId);

    @Query("SELECT lp FROM LessonPlan lp WHERE lp.classSubject.teacher.id = :teacherId AND lp.date = :date")
    List<LessonPlan> findByTeacherAndDate(@Param("teacherId") Long teacherId,
                                          @Param("date") LocalDate date);

    @Query("SELECT lp FROM LessonPlan lp WHERE lp.approvedBy IS NULL AND lp.status != 'DRAFT'")
    List<LessonPlan> findPendingApproval();

    @Query("SELECT lp FROM LessonPlan lp WHERE lp.classSubject.id = :classSubjectId AND lp.shift = :shift")
    List<LessonPlan> findByClassSubjectAndShift(@Param("classSubjectId") Long classSubjectId,
                                                @Param("shift") Shift shift);

    @Query("SELECT lp FROM LessonPlan lp WHERE lp.classSubject.id = :classSubjectId AND lp.status = 'APPROVED'")
    List<LessonPlan> findApprovedByClassSubject(@Param("classSubjectId") Long classSubjectId);

    @Query("SELECT COUNT(lp) FROM LessonPlan lp WHERE lp.classSubject.id = :classSubjectId AND lp.status = 'APPROVED'")
    Long countApprovedByClassSubject(@Param("classSubjectId") Long classSubjectId);

    @Query("SELECT lp FROM LessonPlan lp WHERE lp.classSubject.id = :classSubjectId AND lp.status IN ('DRAFT', 'REVISION')")
    List<LessonPlan> findDraftByClassSubject(@Param("classSubjectId") Long classSubjectId);
}