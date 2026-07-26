package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.finance.Bill;
import com.sarana_NIS.newTown_backend.model.enums.BillStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findByStatus(BillStatus status);

    List<Bill> findByDueDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT b FROM Bill b WHERE b.requestedBy.id = :userId")
    List<Bill> findByRequestedById(@Param("userId") Long userId);

    @Query("SELECT COALESCE(SUM(b.amount), 0) FROM Bill b")
    Double sumTotalBills();  // ✅ Returns Double

    @Query("SELECT COALESCE(SUM(b.amount), 0) FROM Bill b WHERE b.status = 'PENDING'")
    Double sumPendingBills();  // ✅ Returns Double

    @Query("SELECT COALESCE(SUM(b.amount), 0) FROM Bill b WHERE b.status = 'APPROVED'")
    Double sumApprovedBills();

    @Query("SELECT COALESCE(SUM(b.amount), 0) FROM Bill b WHERE b.status = 'REJECTED'")
    Double sumRejectedBills();

    @Query("SELECT COUNT(b) FROM Bill b WHERE b.status = :status")
    Long countByStatus(@Param("status") BillStatus status);

    @Query("SELECT COALESCE(SUM(b.amount), 0) FROM Bill b WHERE b.billType = :billType")
    Double sumByBillType(@Param("billType") String billType);

    @Query("SELECT COALESCE(SUM(b.amount), 0) FROM Bill b WHERE b.dueDate BETWEEN :startDate AND :endDate")
    Double sumByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT b FROM Bill b WHERE b.status = 'PENDING' AND b.dueDate < :date")
    List<Bill> findOverdueBills(@Param("date") LocalDate date);
}