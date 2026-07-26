package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.library.Checkout;
import com.sarana_NIS.newTown_backend.model.enums.CheckoutStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    @Query("SELECT c FROM Checkout c WHERE c.student.id = :studentId")
    List<Checkout> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT c FROM Checkout c WHERE c.book.id = :bookId")
    List<Checkout> findByBookId(@Param("bookId") Long bookId);

    @Query("SELECT c FROM Checkout c WHERE c.status = :status")
    List<Checkout> findByStatus(@Param("status") CheckoutStatus status);

    @Query("SELECT c FROM Checkout c WHERE c.student.id = :studentId AND c.status = 'CHECKED_OUT'")
    List<Checkout> findActiveByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT c FROM Checkout c WHERE c.book.id = :bookId AND c.status = 'CHECKED_OUT'")
    List<Checkout> findActiveByBookId(@Param("bookId") Long bookId);

    @Query("SELECT c FROM Checkout c WHERE c.dueDate < :date AND c.status = 'CHECKED_OUT'")
    List<Checkout> findOverdueCheckouts(@Param("date") LocalDate date);

    @Query("SELECT c FROM Checkout c WHERE c.dueDate BETWEEN :startDate AND :endDate")
    List<Checkout> findByDueDateBetween(@Param("startDate") LocalDate startDate,
                                        @Param("endDate") LocalDate endDate);

    @Query("SELECT c FROM Checkout c WHERE c.returnDate BETWEEN :startDate AND :endDate")
    List<Checkout> findByReturnDateBetween(@Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(c) FROM Checkout c WHERE c.book.id = :bookId AND c.status = 'CHECKED_OUT'")
    Long countActiveByBookId(@Param("bookId") Long bookId);

    @Query("SELECT COUNT(c) FROM Checkout c WHERE c.student.id = :studentId AND c.status = 'CHECKED_OUT'")
    Long countActiveByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT c FROM Checkout c WHERE c.fineAmount IS NOT NULL AND c.finePaid = false")
    List<Checkout> findUnpaidFines();

    @Query("SELECT c.student.id, COUNT(c) FROM Checkout c " +
            "WHERE c.status = 'CHECKED_OUT' GROUP BY c.student.id ORDER BY COUNT(c) DESC")
    List<Object[]> findStudentsWithMostBooks();

    @Query("SELECT COUNT(c) FROM Checkout c WHERE c.status = 'CHECKED_OUT'")
    Long countActiveCheckouts();

    @Query("SELECT c.book.id, COUNT(c) FROM Checkout c " +
            "WHERE c.status = 'CHECKED_OUT' OR c.status = 'RETURNED' " +
            "GROUP BY c.book.id ORDER BY COUNT(c) DESC")
    List<Object[]> findMostBorrowedBooksCount();
}