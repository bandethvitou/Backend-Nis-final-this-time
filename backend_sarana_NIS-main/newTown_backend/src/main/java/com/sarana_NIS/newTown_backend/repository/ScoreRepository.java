package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.academic.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query("SELECT s FROM Score s WHERE s.student.id = :studentId AND s.classSubject.id = :classSubjectId AND s.term = :term")
    Optional<Score> findByStudentClassSubjectTerm(@Param("studentId") Long studentId,
                                                  @Param("classSubjectId") Long classSubjectId,
                                                  @Param("term") String term);

    @Query("SELECT s FROM Score s WHERE s.classSubject.id = :classSubjectId AND s.term = :term")
    List<Score> findByClassSubjectAndTerm(@Param("classSubjectId") Long classSubjectId,
                                          @Param("term") String term);

    @Query("SELECT s FROM Score s WHERE s.student.id = :studentId AND s.term = :term")
    List<Score> findByStudentAndTerm(@Param("studentId") Long studentId,
                                     @Param("term") String term);

    @Query("SELECT AVG(s.totalScore) FROM Score s WHERE s.classSubject.id = :classSubjectId AND s.term = :term")
    Double findAverageByClassSubjectTerm(@Param("classSubjectId") Long classSubjectId,
                                         @Param("term") String term);

    @Query("SELECT s FROM Score s WHERE s.classSubject.id = :classSubjectId AND s.totalScore < :threshold")
    List<Score> findLowScoresByClassSubject(@Param("classSubjectId") Long classSubjectId,
                                            @Param("threshold") Double threshold);

    @Query("SELECT s FROM Score s WHERE s.classSubject.id = :classSubjectId AND s.totalScore > :threshold")
    List<Score> findHighScoresByClassSubject(@Param("classSubjectId") Long classSubjectId,
                                             @Param("threshold") Double threshold);

    @Query("SELECT COUNT(s) FROM Score s WHERE s.classSubject.id = :classSubjectId AND s.submittedAt IS NOT NULL")
    Long countSubmittedByClassSubject(@Param("classSubjectId") Long classSubjectId);

    @Query("SELECT s FROM Score s WHERE s.classSubject.id = :classSubjectId AND s.submittedAt IS NULL")
    List<Score> findNotSubmittedByClassSubject(@Param("classSubjectId") Long classSubjectId);

    @Query("SELECT s.student.id, AVG(s.totalScore) FROM Score s " +
            "WHERE s.classSubject.id = :classSubjectId GROUP BY s.student.id")
    List<Object[]> findStudentAveragesByClassSubject(@Param("classSubjectId") Long classSubjectId);

    @Query("SELECT s FROM Score s WHERE s.approvedBy IS NULL AND s.submittedAt IS NOT NULL")
    List<Score> findPendingApproval();

    @Query("SELECT s FROM Score s WHERE s.approvedAt >= :date")
    List<Score> findApprovedSince(@Param("date") LocalDate date);

    @Query("SELECT MAX(s.totalScore) FROM Score s WHERE s.classSubject.id = :classSubjectId")
    Double findHighestScoreByClassSubject(@Param("classSubjectId") Long classSubjectId);

    @Query("SELECT MIN(s.totalScore) FROM Score s WHERE s.classSubject.id = :classSubjectId")
    Double findLowestScoreByClassSubject(@Param("classSubjectId") Long classSubjectId);
}