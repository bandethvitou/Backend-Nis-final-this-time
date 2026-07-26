package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.academic.ScoreStructure;
import com.sarana_NIS.newTown_backend.model.enums.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreStructureRepository extends JpaRepository<ScoreStructure, Long> {

    @Query("SELECT ss FROM ScoreStructure ss WHERE ss.level = :level AND ss.academicYear = :academicYear AND ss.term = :term")
    List<ScoreStructure> findByLevelAndAcademicYearAndTerm(@Param("level") Level level,
                                                           @Param("academicYear") String academicYear,
                                                           @Param("term") String term);

    @Query("SELECT ss FROM ScoreStructure ss WHERE ss.classEntity.id = :classId AND ss.academicYear = :academicYear")
    Optional<ScoreStructure> findByClassAndAcademicYear(@Param("classId") Long classId,
                                                        @Param("academicYear") String academicYear);

    @Query("SELECT ss FROM ScoreStructure ss WHERE ss.classEntity.id = :classId AND ss.subject.id = :subjectId")
    Optional<ScoreStructure> findByClassAndSubject(@Param("classId") Long classId,
                                                   @Param("subjectId") Long subjectId);

    @Query("SELECT ss FROM ScoreStructure ss WHERE ss.isActive = true AND ss.effectiveFrom <= CURRENT_TIMESTAMP " +
            "AND (ss.effectiveTo IS NULL OR ss.effectiveTo >= CURRENT_TIMESTAMP)")
    List<ScoreStructure> findActiveStructures();

    @Query("SELECT ss FROM ScoreStructure ss WHERE ss.level = :level AND ss.isActive = true")
    List<ScoreStructure> findActiveByLevel(@Param("level") Level level);

    @Query("SELECT ss FROM ScoreStructure ss WHERE ss.classEntity.id = :classId AND ss.isActive = true")
    List<ScoreStructure> findActiveByClass(@Param("classId") Long classId);

    @Query("SELECT ss FROM ScoreStructure ss WHERE ss.level = :level AND ss.term = :term")
    List<ScoreStructure> findByLevelAndTerm(@Param("level") Level level, @Param("term") String term);

    @Query("SELECT COUNT(ss) FROM ScoreStructure ss WHERE ss.classEntity.id = :classId")
    Long countByClassId(@Param("classId") Long classId);
}