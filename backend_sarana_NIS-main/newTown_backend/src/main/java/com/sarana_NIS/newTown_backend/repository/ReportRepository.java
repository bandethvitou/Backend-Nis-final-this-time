package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.management.Report;
import com.sarana_NIS.newTown_backend.model.enums.ReportStatus;
import com.sarana_NIS.newTown_backend.model.enums.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("SELECT r FROM Report r WHERE r.type = :type")
    List<Report> findByType(@Param("type") ReportType type);

    @Query("SELECT r FROM Report r WHERE r.status = :status")
    List<Report> findByStatus(@Param("status") ReportStatus status);

    @Query("SELECT r FROM Report r WHERE r.submittedBy.id = :userId")
    List<Report> findBySubmittedBy(@Param("userId") Long userId);

    @Query("SELECT r FROM Report r WHERE r.submittedTo.id = :userId")
    List<Report> findBySubmittedTo(@Param("userId") Long userId);

    @Query("SELECT r FROM Report r WHERE r.department.id = :departmentId")
    List<Report> findByDepartmentId(@Param("departmentId") Long departmentId);

    @Query("SELECT r FROM Report r WHERE r.status = 'SUBMITTED' OR r.status = 'UNDER_REVIEW'")
    List<Report> findPendingReview();

    @Query("SELECT r FROM Report r WHERE r.isConfidential = true")
    List<Report> findConfidentialReports();

    @Query("SELECT r FROM Report r WHERE r.revisionRequired = true")
    List<Report> findRevisionRequired();

    // ❌ DELETE THIS METHOD - Report doesn't have reportPeriodStart/reportPeriodEnd
    // @Query("SELECT r FROM Report r WHERE r.reportPeriodStart BETWEEN :startDate AND :endDate")
    // List<Report> findByReportPeriodBetween(@Param("startDate") LocalDate startDate,
    //                                        @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(r) FROM Report r WHERE r.status = 'PENDING' OR r.status = 'SUBMITTED'")
    Long countPendingReports();

    @Query("SELECT COUNT(r) FROM Report r WHERE r.status = 'APPROVED'")
    Long countApprovedReports();

    @Query("SELECT r.type, COUNT(r) FROM Report r GROUP BY r.type")
    List<Object[]> countReportsByType();

    @Query("SELECT r.status, COUNT(r) FROM Report r GROUP BY r.status")
    List<Object[]> countReportsByStatus();

    @Query("SELECT r.department.id, COUNT(r) FROM Report r GROUP BY r.department.id")
    List<Object[]> countReportsByDepartment();

    @Query("SELECT r FROM Report r WHERE r.submittedBy.id = :userId AND r.status = 'DRAFT'")
    List<Report> findDraftByUser(@Param("userId") Long userId);

    @Query("SELECT r FROM Report r WHERE r.submittedTo.id = :userId AND r.status = 'SUBMITTED'")
    List<Report> findSubmittedToReviewer(@Param("userId") Long userId);
}