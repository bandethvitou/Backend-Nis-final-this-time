package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.management.Goal;
import com.sarana_NIS.newTown_backend.model.enums.GoalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

    @Query("SELECT g FROM Goal g WHERE g.manager.id = :managerId")
    List<Goal> findByManagerId(@Param("managerId") Long managerId);

    @Query("SELECT g FROM Goal g WHERE g.status = :status")
    List<Goal> findByStatus(@Param("status") GoalStatus status);

    @Query("SELECT g FROM Goal g WHERE g.deadline < :date AND g.status != 'COMPLETED'")
    List<Goal> findOverdueGoals(@Param("date") LocalDate date);

    @Query("SELECT g FROM Goal g WHERE g.deadline BETWEEN :startDate AND :endDate")
    List<Goal> findByDeadlineBetween(@Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);

    @Query("SELECT g FROM Goal g WHERE g.manager.id = :managerId AND g.status IN ('IN_PROGRESS', 'NOT_STARTED')")
    List<Goal> findActiveByManager(@Param("managerId") Long managerId);

    @Query("SELECT g FROM Goal g WHERE g.current >= g.target")
    List<Goal> findCompletedGoals();

    @Query("SELECT g FROM Goal g WHERE g.current < g.target AND g.status != 'COMPLETED'")
    List<Goal> findIncompleteGoals();

    @Query("SELECT COUNT(g) FROM Goal g WHERE g.status = :status")
    Long countByStatus(@Param("status") GoalStatus status);

    @Query("SELECT AVG(g.current / g.target * 100) FROM Goal g WHERE g.status != 'COMPLETED'")
    Double findAverageProgress();

    @Query("SELECT g.manager.id, COUNT(g) FROM Goal g GROUP BY g.manager.id")
    List<Object[]> countGoalsByManager();

    @Query("SELECT g FROM Goal g WHERE g.manager.id = :managerId AND g.deadline >= :date ORDER BY g.deadline ASC")
    List<Goal> findUpcomingByManager(@Param("managerId") Long managerId,
                                     @Param("date") LocalDate date);

    @Query("SELECT g FROM Goal g WHERE g.status = 'NOT_STARTED' AND g.deadline < :date")
    List<Goal> findNotStartedOverdue(@Param("date") LocalDate date);
}