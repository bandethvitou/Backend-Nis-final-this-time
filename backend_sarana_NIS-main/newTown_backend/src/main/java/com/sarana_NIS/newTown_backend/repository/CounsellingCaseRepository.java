package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.counselling.CounsellingCase;
import com.sarana_NIS.newTown_backend.model.enums.CaseStatus;
import com.sarana_NIS.newTown_backend.model.enums.IssueSeverity;
import com.sarana_NIS.newTown_backend.model.enums.IssueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CounsellingCaseRepository extends JpaRepository<CounsellingCase, Long> {

    @Query("SELECT c FROM CounsellingCase c WHERE c.student.id = :studentId")
    List<CounsellingCase> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT c FROM CounsellingCase c WHERE c.student.id = :studentId AND c.status != 'CLOSED'")
    List<CounsellingCase> findActiveByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT c FROM CounsellingCase c WHERE c.issueType = :issueType")
    List<CounsellingCase> findByIssueType(@Param("issueType") IssueType issueType);

    @Query("SELECT c FROM CounsellingCase c WHERE c.severity = :severity")
    List<CounsellingCase> findBySeverity(@Param("severity") IssueSeverity severity);

    @Query("SELECT c FROM CounsellingCase c WHERE c.status = :status")
    List<CounsellingCase> findByStatus(@Param("status") CaseStatus status);

    @Query("SELECT c FROM CounsellingCase c WHERE c.severity = 'HIGH' OR c.severity = 'CRITICAL'")
    List<CounsellingCase> findHighPriorityCases();

    @Query("SELECT c FROM CounsellingCase c WHERE c.status = 'ACTIVE' ORDER BY c.severity DESC")
    List<CounsellingCase> findActiveOrderedBySeverity();

    @Query("SELECT COUNT(c) FROM CounsellingCase c WHERE c.status = 'ACTIVE'")
    Long countActiveCases();

    @Query("SELECT COUNT(c) FROM CounsellingCase c WHERE c.severity = :severity AND c.status = 'ACTIVE'")
    Long countActiveBySeverity(@Param("severity") IssueSeverity severity);

    @Query("SELECT COUNT(c) FROM CounsellingCase c WHERE c.student.id = :studentId AND c.status = 'ACTIVE'")
    Long countActiveByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT c FROM CounsellingCase c WHERE c.closedAt IS NULL AND c.openedAt < :date")
    List<CounsellingCase> findLongStandingCases(@Param("date") LocalDate date);

    @Query("SELECT c.student.id, COUNT(c) FROM CounsellingCase c " +
            "WHERE c.status = 'ACTIVE' GROUP BY c.student.id ORDER BY COUNT(c) DESC")
    List<Object[]> findStudentsWithMostActiveCases();
}