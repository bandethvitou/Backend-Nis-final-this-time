package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.core.SchoolSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SchoolSettingRepository extends JpaRepository<SchoolSetting, Long> {

    @Query("SELECT s FROM SchoolSetting s WHERE s.id = (SELECT MIN(s2.id) FROM SchoolSetting s2)")
    Optional<SchoolSetting> findFirst();

    @Query("SELECT s.schoolName FROM SchoolSetting s WHERE s.id = (SELECT MIN(s2.id) FROM SchoolSetting s2)")
    String findSchoolName();

    @Query("SELECT s.academicYear FROM SchoolSetting s WHERE s.id = (SELECT MIN(s2.id) FROM SchoolSetting s2)")
    String findCurrentAcademicYear();

    @Query("SELECT s.currentTerm FROM SchoolSetting s WHERE s.id = (SELECT MIN(s2.id) FROM SchoolSetting s2)")
    String findCurrentTerm();

    @Modifying
    @Transactional
    @Query("UPDATE SchoolSetting s SET s.logoUrl = :logoUrl WHERE s.id = :id")
    void updateLogoUrl(@Param("id") Long id, @Param("logoUrl") String logoUrl);

    @Modifying
    @Transactional
    @Query("UPDATE SchoolSetting s SET s.academicYear = :academicYear WHERE s.id = :id")
    void updateAcademicYear(@Param("id") Long id, @Param("academicYear") String academicYear);

    @Query("SELECT s.timezone FROM SchoolSetting s WHERE s.id = (SELECT MIN(s2.id) FROM SchoolSetting s2)")
    String findTimezone();
}