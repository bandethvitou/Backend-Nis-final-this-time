package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.finance.Fee;
import com.sarana_NIS.newTown_backend.model.enums.FeeCategory;
import com.sarana_NIS.newTown_backend.model.enums.FeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Long> {

    @Query("SELECT f FROM Fee f WHERE f.student.id = :studentId")
    List<Fee> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT f FROM Fee f WHERE f.student.id = :studentId AND f.status = :status")
    List<Fee> findByStudentAndStatus(@Param("studentId") Long studentId,
                                     @Param("status") FeeStatus status);

    @Query("SELECT f FROM Fee f WHERE f.category = :category")
    List<Fee> findByCategory(@Param("category") FeeCategory category);

    @Query("SELECT f FROM Fee f WHERE f.dueDate < :date AND f.status = 'DUE'")
    List<Fee> findOverdueFees(@Param("date") LocalDate date);

    @Query("SELECT f FROM Fee f WHERE f.dueDate BETWEEN :startDate AND :endDate")
    List<Fee> findByDueDateBetween(@Param("startDate") LocalDate startDate,
                                   @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(f.amount) FROM Fee f WHERE f.student.id = :studentId AND f.status = 'DUE'")
    BigDecimal sumOutstandingByStudent(@Param("studentId") Long studentId);

    @Query("SELECT SUM(f.amount) FROM Fee f WHERE f.status = 'DUE'")
    BigDecimal sumTotalOutstanding();

    @Query("SELECT SUM(f.amount) FROM Fee f WHERE f.status = 'PAID'")
    BigDecimal sumTotalCollected();

    @Query("SELECT SUM(f.amount) FROM Fee f WHERE f.status = 'OVERDUE'")
    BigDecimal sumOverdueFees();

    @Query("SELECT COUNT(DISTINCT f.student.id) FROM Fee f WHERE f.status = 'OVERDUE'")
    Long countDefaulters();

    @Query("SELECT f FROM Fee f WHERE f.status = :status")
    List<Fee> findByStatus(@Param("status") FeeStatus status);

    // ✅ ADD THIS METHOD
    @Query("SELECT COUNT(f) FROM Fee f WHERE f.status = 'OVERDUE' OR (f.status = 'DUE' AND f.dueDate < CURRENT_DATE)")
    long countOverdueFees();

    @Query("SELECT f.student.id, SUM(f.amount) FROM Fee f WHERE f.status = 'DUE' GROUP BY f.student.id")
    List<Object[]> findStudentsWithOutstandingFees();

    @Query("SELECT COUNT(f) FROM Fee f WHERE f.student.id = :studentId AND f.status = 'OVERDUE'")
    Long countOverdueByStudent(@Param("studentId") Long studentId);
}