package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.academic.AcademicLevel;
import com.sarana_NIS.newTown_backend.model.enums.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcademicLevelRepository extends JpaRepository<AcademicLevel, Long> {

    Optional<AcademicLevel> findByName(String name);

    Optional<AcademicLevel> findByCode(String code);

    List<AcademicLevel> findAllByOrderBySequenceOrderAsc();

    @Query("SELECT al FROM AcademicLevel al WHERE al.minAge <= :age AND al.maxAge >= :age")
    List<AcademicLevel> findLevelsForAge(@Param("age") Integer age);

    @Query("SELECT al FROM AcademicLevel al LEFT JOIN FETCH al.classes WHERE al.id = :id")
    Optional<AcademicLevel> findByIdWithClasses(@Param("id") Long id);

    @Query("SELECT al FROM AcademicLevel al WHERE al.name = :name")
    Optional<AcademicLevel> findByNameIgnoreCase(@Param("name") String name);

    @Query("SELECT COUNT(c) FROM Class c WHERE c.level.id = :levelId")
    Long countClassesByLevelId(@Param("levelId") Long levelId);

    @Query("SELECT al FROM AcademicLevel al WHERE al.sequenceOrder = " +
            "(SELECT MAX(al2.sequenceOrder) FROM AcademicLevel al2)")
    Optional<AcademicLevel> findHighestLevel();

    @Query("SELECT al FROM AcademicLevel al WHERE al.sequenceOrder = " +
            "(SELECT MIN(al2.sequenceOrder) FROM AcademicLevel al2)")
    Optional<AcademicLevel> findLowestLevel();
}