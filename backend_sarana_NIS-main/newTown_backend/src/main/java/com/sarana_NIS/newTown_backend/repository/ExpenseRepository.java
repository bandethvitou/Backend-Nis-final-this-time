package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.finance.Expense;
import com.sarana_NIS.newTown_backend.model.enums.ExpenseCategory;
import com.sarana_NIS.newTown_backend.model.enums.ExpenseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Find by status
    List<Expense> findByStatus(ExpenseStatus status);

    // Find by category
    List<Expense> findByCategory(ExpenseCategory category);

    // Find by date range
    List<Expense> findByExpenseDateBetween(LocalDate startDate, LocalDate endDate);

    // Find pending approval
    @Query("SELECT e FROM Expense e WHERE e.status = :status")
    List<Expense> findPendingApproval(@Param("status") ExpenseStatus status);

    default List<Expense> findPendingApproval() {
        return findPendingApproval(ExpenseStatus.PENDING);
    }

    // Sum methods - using Long for amounts
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e")
    Double sumTotalExpenses();

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.status = 'PAID'")
    Double sumPaidExpenses();

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.status = 'REJECTED'")
    Double sumRejectedExpenses();

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.status = 'PENDING'")
    Double sumPendingExpenses();

    // Count by status
    @Query("SELECT COUNT(e) FROM Expense e WHERE e.status = :status")
    Long countByStatus(@Param("status") ExpenseStatus status);

    // Sum by category
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.category = :category")
    Double sumByCategory(@Param("category") ExpenseCategory category);

    // Sum by date range
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.expenseDate BETWEEN :startDate AND :endDate")
    Double sumByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Find by user
    @Query("SELECT e FROM Expense e WHERE e.requestedBy.id = :userId")
    List<Expense> findByRequestedBy(@Param("userId") Long userId);

    // Find by status and user
    @Query("SELECT e FROM Expense e WHERE e.status = :status AND e.requestedBy.id = :userId")
    List<Expense> findByStatusAndRequestedBy(@Param("status") ExpenseStatus status, @Param("userId") Long userId);

    // Monthly summary
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE YEAR(e.expenseDate) = :year AND MONTH(e.expenseDate) = :month")
    Double sumByMonth(@Param("year") int year, @Param("month") int month);

    // Yearly summary
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE YEAR(e.expenseDate) = :year")
    Double sumByYear(@Param("year") int year);
}