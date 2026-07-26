package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.finance.Salary;
import com.sarana_NIS.newTown_backend.model.enums.SalaryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

    // Find by staff ID
    List<Salary> findByStaffId(Long staffId);

    // Find by month
    List<Salary> findByMonth(String month);

    // Find by status
    List<Salary> findByStatus(SalaryStatus status);

    // Find by staff ID and month
    @Query("SELECT s FROM Salary s WHERE s.staff.id = :staffId AND s.month = :month")
    List<Salary> findByStaffIdAndMonth(@Param("staffId") Long staffId, @Param("month") String month);

    // Find by month and status
    @Query("SELECT s FROM Salary s WHERE s.month = :month AND s.status = :status")
    List<Salary> findByMonthAndStatus(@Param("month") String month, @Param("status") SalaryStatus status);

    // Sum methods
    @Query("SELECT COALESCE(SUM(s.total), 0) FROM Salary s WHERE s.month = :month")
    BigDecimal sumTotalByMonth(@Param("month") String month);

    @Query("SELECT COALESCE(SUM(s.total), 0) FROM Salary s WHERE s.month = :month AND s.status = 'PAID'")
    BigDecimal sumPaidByMonth(@Param("month") String month);

    @Query("SELECT COALESCE(SUM(s.total), 0) FROM Salary s WHERE s.month = :month AND s.status = 'PENDING'")
    BigDecimal sumPendingByMonth(@Param("month") String month);

    // Count methods
    @Query("SELECT COUNT(DISTINCT s.staff.id) FROM Salary s WHERE s.month = :month")
    Long countStaffByMonth(@Param("month") String month);

    @Query("SELECT COUNT(DISTINCT s.staff.id) FROM Salary s WHERE s.month = :month AND s.status = 'PAID'")
    Long countPaidByMonth(@Param("month") String month);

    @Query("SELECT COUNT(DISTINCT s.staff.id) FROM Salary s WHERE s.month = :month AND s.status = 'PENDING'")
    Long countPendingByMonth(@Param("month") String month);

    // Additional useful queries
    @Query("SELECT COALESCE(SUM(s.total), 0) FROM Salary s WHERE s.staff.id = :staffId AND s.month = :month")
    BigDecimal sumByStaffAndMonth(@Param("staffId") Long staffId, @Param("month") String month);

    @Query("SELECT COALESCE(SUM(s.total), 0) FROM Salary s WHERE s.staff.id = :staffId")
    BigDecimal sumTotalByStaff(@Param("staffId") Long staffId);

    @Query("SELECT COUNT(s) FROM Salary s WHERE s.month = :month AND s.status = :status")
    Long countByMonthAndStatus(@Param("month") String month, @Param("status") SalaryStatus status);
}