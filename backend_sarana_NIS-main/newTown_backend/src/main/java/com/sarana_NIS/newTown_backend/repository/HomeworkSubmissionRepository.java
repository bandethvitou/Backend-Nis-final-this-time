package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.academic.HomeworkSubmission;
import com.sarana_NIS.newTown_backend.model.enums.HomeworkStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HomeworkSubmissionRepository extends JpaRepository<HomeworkSubmission, Long> {

    @Query("SELECT hs FROM HomeworkSubmission hs WHERE hs.homework.id = :homeworkId")
    List<HomeworkSubmission> findByHomeworkId(@Param("homeworkId") Long homeworkId);

    @Query("SELECT hs FROM HomeworkSubmission hs WHERE hs.student.id = :studentId")
    List<HomeworkSubmission> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT hs FROM HomeworkSubmission hs WHERE hs.homework.id = :homeworkId AND hs.student.id = :studentId")
    Optional<HomeworkSubmission> findByHomeworkAndStudent(@Param("homeworkId") Long homeworkId,
                                                          @Param("studentId") Long studentId);

    @Query("SELECT hs FROM HomeworkSubmission hs WHERE hs.status = :status")
    List<HomeworkSubmission> findByStatus(@Param("status") HomeworkStatus status);

    @Query("SELECT hs FROM HomeworkSubmission hs WHERE hs.homework.id = :homeworkId AND hs.status = 'SUBMITTED'")
    List<HomeworkSubmission> findSubmittedByHomework(@Param("homeworkId") Long homeworkId);

    @Query("SELECT hs FROM HomeworkSubmission hs WHERE hs.homework.id = :homeworkId AND hs.status = 'LATE'")
    List<HomeworkSubmission> findLateByHomework(@Param("homeworkId") Long homeworkId);

    @Query("SELECT COUNT(hs) FROM HomeworkSubmission hs WHERE hs.homework.id = :homeworkId AND hs.status = 'SUBMITTED'")
    Long countSubmittedByHomework(@Param("homeworkId") Long homeworkId);

    @Query("SELECT AVG(hs.score) FROM HomeworkSubmission hs WHERE hs.homework.id = :homeworkId AND hs.score IS NOT NULL")
    Double findAverageScoreByHomework(@Param("homeworkId") Long homeworkId);

    @Query("SELECT hs FROM HomeworkSubmission hs WHERE hs.reviewedByTeacher = false AND hs.submissionDate IS NOT NULL")
    List<HomeworkSubmission> findPendingTeacherReview();

    @Query("SELECT hs FROM HomeworkSubmission hs WHERE hs.reviewedByParent = false AND hs.submissionDate IS NOT NULL")
    List<HomeworkSubmission> findPendingParentReview();

    @Query("SELECT hs FROM HomeworkSubmission hs WHERE hs.student.id = :studentId AND hs.status = 'PENDING'")
    List<HomeworkSubmission> findPendingByStudent(@Param("studentId") Long studentId);
}