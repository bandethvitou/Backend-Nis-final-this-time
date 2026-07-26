package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.management.Task;
import com.sarana_NIS.newTown_backend.model.enums.Priority;
import com.sarana_NIS.newTown_backend.model.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.assignedTo.id = :userId")
    List<Task> findByAssignedTo(@Param("userId") Long userId);

    @Query("SELECT t FROM Task t WHERE t.assignedBy.id = :userId")
    List<Task> findByAssignedBy(@Param("userId") Long userId);

    @Query("SELECT t FROM Task t WHERE t.status = :status")
    List<Task> findByStatus(@Param("status") TaskStatus status);

    @Query("SELECT t FROM Task t WHERE t.priority = :priority")
    List<Task> findByPriority(@Param("priority") Priority priority);

    @Query("SELECT t FROM Task t WHERE t.assignedTo.id = :userId AND t.status = 'PENDING'")
    List<Task> findPendingByAssignee(@Param("userId") Long userId);

    @Query("SELECT t FROM Task t WHERE t.assignedTo.id = :userId AND t.status = 'IN_PROGRESS'")
    List<Task> findInProgressByAssignee(@Param("userId") Long userId);

    @Query("SELECT t FROM Task t WHERE t.assignedTo.id = :userId AND t.dueDate < :date AND t.status != 'COMPLETED'")
    List<Task> findOverdueByAssignee(@Param("userId") Long userId,
                                     @Param("date") LocalDate date);

    @Query("SELECT t FROM Task t WHERE t.dueDate BETWEEN :startDate AND :endDate")
    List<Task> findByDueDateBetween(@Param("startDate") LocalDate startDate,
                                    @Param("endDate") LocalDate endDate);

    @Query("SELECT t FROM Task t WHERE t.dueDate >= :date ORDER BY t.dueDate ASC")
    List<Task> findUpcomingTasks(@Param("date") LocalDate date);

    @Query("SELECT t FROM Task t WHERE t.assignedTo.id = :userId ORDER BY t.createdAt DESC")
    Page<Task> findByAssigneeId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.status = 'PENDING' OR t.status = 'IN_PROGRESS'")
    Long countActiveTasks();

    @Query("SELECT COUNT(t) FROM Task t WHERE t.status = 'COMPLETED'")
    Long countCompletedTasks();

    @Query("SELECT COUNT(t) FROM Task t WHERE t.status = 'PENDING' AND t.dueDate < :date")
    Long countOverdueTasks(@Param("date") LocalDate date);

    @Query("SELECT t.assignedTo.id, COUNT(t) FROM Task t GROUP BY t.assignedTo.id")
    List<Object[]> countTasksByAssignee();

    @Query("SELECT t.priority, COUNT(t) FROM Task t GROUP BY t.priority")
    List<Object[]> countTasksByPriority();

    // ✅ ADD THIS MISSING METHOD
    @Query("SELECT t FROM Task t WHERE t.dueDate < :date AND t.status != 'COMPLETED' AND t.status != 'CANCELLED'")
    List<Task> findOverdueTasks(@Param("date") LocalDate date);
}