package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.admissions.Application;
import com.sarana_NIS.newTown_backend.model.enums.ApplicationStatus;
import com.sarana_NIS.newTown_backend.model.enums.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Query("SELECT a FROM Application a WHERE a.status = :status")
    List<Application> findByStatus(@Param("status") ApplicationStatus status);

    @Query("SELECT a FROM Application a WHERE a.level = :level")
    List<Application> findByLevel(@Param("level") Level level);

    @Query("SELECT a FROM Application a WHERE a.parentEmail = :email")
    List<Application> findByParentEmail(@Param("email") String email);

    @Query("SELECT a FROM Application a WHERE a.parentPhone = :phone")
    List<Application> findByParentPhone(@Param("phone") String phone);

    @Query("SELECT a FROM Application a WHERE a.status IN :statuses")
    List<Application> findByStatusIn(@Param("statuses") List<ApplicationStatus> statuses);

    @Query("SELECT a FROM Application a WHERE a.status = 'UNDER_REVIEW'")
    List<Application> findPendingReview();

    @Query("SELECT a FROM Application a WHERE a.status = 'APPROVED' AND a.enrolledDate IS NULL")
    List<Application> findApprovedNotEnrolled();

    @Query("SELECT a FROM Application a WHERE a.submittedDate BETWEEN :start AND :end")
    List<Application> findBySubmittedDateBetween(@Param("start") LocalDateTime start,
                                                 @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(a) FROM Application a WHERE a.status = :status")
    Long countByStatus(@Param("status") ApplicationStatus status);

    @Query("SELECT COUNT(a) FROM Application a WHERE a.level = :level AND a.status = 'ENROLLED'")
    Long countEnrolledByLevel(@Param("level") Level level);

    @Query("SELECT a FROM Application a WHERE a.status IN ('SUBMITTED', 'UNDER_REVIEW', 'DOCUMENTS_REQUESTED')")
    List<Application> findActiveApplications();

    @Query("SELECT a FROM Application a WHERE a.status IN ('APPROVED', 'INVOICE_SENT', 'PAID', 'ENROLLED')")
    List<Application> findProcessedApplications();

    @Query("SELECT a FROM Application a WHERE a.studentName LIKE CONCAT('%', :name, '%') " +
            "OR a.parentName LIKE CONCAT('%', :name, '%')")
    Page<Application> searchApplications(@Param("name") String name, Pageable pageable);

    @Query("SELECT DATE(a.submittedDate), COUNT(a) FROM Application a " +
            "WHERE a.submittedDate BETWEEN :start AND :end GROUP BY DATE(a.submittedDate)")
    List<Object[]> findDailyApplicationCount(@Param("start") LocalDateTime start,
                                             @Param("end") LocalDateTime end);

    @Query("SELECT a.level, COUNT(a) FROM Application a WHERE a.status = 'ENROLLED' GROUP BY a.level")
    List<Object[]> findEnrolledByLevel();

    @Query("SELECT COUNT(a) FROM Application a WHERE a.status = 'REJECTED' AND a.reviewNotes IS NOT NULL")
    Long countRejectedWithNotes();

    @Query("SELECT a FROM Application a WHERE a.enrolledDate IS NOT NULL AND a.enrolledDate >= :date")
    List<Application> findRecentEnrollments(@Param("date") LocalDateTime date);

    @Query("SELECT AVG(TIMESTAMPDIFF(DAY, a.submittedDate, a.enrolledDate)) FROM Application a " +
            "WHERE a.status = 'ENROLLED'")
    Double findAverageEnrollmentTime();
}