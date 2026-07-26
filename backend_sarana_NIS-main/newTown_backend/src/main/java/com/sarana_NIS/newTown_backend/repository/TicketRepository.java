package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.it.Ticket;
import com.sarana_NIS.newTown_backend.model.enums.Priority;
import com.sarana_NIS.newTown_backend.model.enums.TicketCategory;
import com.sarana_NIS.newTown_backend.model.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.reportedBy.id = :userId")
    List<Ticket> findByReportedBy(@Param("userId") Long userId);

    @Query("SELECT t FROM Ticket t WHERE t.assignedTo.id = :userId")
    List<Ticket> findByAssignedTo(@Param("userId") Long userId);

    @Query("SELECT t FROM Ticket t WHERE t.status = :status")
    List<Ticket> findByStatus(@Param("status") TicketStatus status);

    @Query("SELECT t FROM Ticket t WHERE t.category = :category")
    List<Ticket> findByCategory(@Param("category") TicketCategory category);

    @Query("SELECT t FROM Ticket t WHERE t.priority = :priority")
    List<Ticket> findByPriority(@Param("priority") Priority priority);

    @Query("SELECT t FROM Ticket t WHERE t.status IN ('OPEN', 'IN_PROGRESS')")
    List<Ticket> findOpenTickets();

    @Query("SELECT t FROM Ticket t WHERE t.status = 'OPEN' ORDER BY t.createdAt ASC")
    List<Ticket> findOldestOpenTickets();

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.status = 'OPEN' OR t.status = 'IN_PROGRESS'")
    Long countOpenTickets();

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.status = 'RESOLVED' AND t.resolutionNotes IS NOT NULL")
    Long countResolvedTickets();

    @Query("SELECT t FROM Ticket t WHERE t.assignedTo IS NULL AND t.status = 'OPEN'")
    List<Ticket> findUnassignedTickets();

    @Query("SELECT t FROM Ticket t WHERE t.status = 'IN_PROGRESS' AND t.assignedTo.id = :userId")
    List<Ticket> findInProgressByAssignee(@Param("userId") Long userId);

    @Query("SELECT t.category, COUNT(t) FROM Ticket t GROUP BY t.category")
    List<Object[]> countTicketsByCategory();

    @Query("SELECT t.priority, COUNT(t) FROM Ticket t GROUP BY t.priority")
    List<Object[]> countTicketsByPriority();

    @Query("SELECT t FROM Ticket t WHERE t.createdAt BETWEEN :start AND :end")
    List<Ticket> findByCreatedAtBetween(@Param("start") LocalDateTime start,
                                        @Param("end") LocalDateTime end);

    @Query("SELECT AVG(TIMESTAMPDIFF(HOUR, t.createdAt, t.updatedAt)) FROM Ticket t " +
            "WHERE t.status = 'RESOLVED' OR t.status = 'CLOSED'")
    Double findAverageResolutionTime();
}