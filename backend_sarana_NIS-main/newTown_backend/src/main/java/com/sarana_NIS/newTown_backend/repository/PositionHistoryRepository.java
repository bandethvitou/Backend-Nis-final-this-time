package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.position.PositionHistory;
import com.sarana_NIS.newTown_backend.model.enums.ActionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PositionHistoryRepository extends JpaRepository<PositionHistory, Long> {

    @Query("SELECT ph FROM PositionHistory ph WHERE ph.staffPosition.staff.id = :staffId")
    List<PositionHistory> findByStaffId(@Param("staffId") Long staffId);

    @Query("SELECT ph FROM PositionHistory ph WHERE ph.staffPosition.position.id = :positionId")
    List<PositionHistory> findByPositionId(@Param("positionId") Long positionId);

    @Query("SELECT ph FROM PositionHistory ph WHERE ph.staffPosition.id = :staffPositionId")
    List<PositionHistory> findByStaffPositionId(@Param("staffPositionId") Long staffPositionId);

    @Query("SELECT ph FROM PositionHistory ph WHERE ph.action = :action")
    List<PositionHistory> findByAction(@Param("action") ActionType action);

    @Query("SELECT ph FROM PositionHistory ph WHERE ph.changedAt BETWEEN :start AND :end")
    List<PositionHistory> findByChangedAtBetween(@Param("start") LocalDateTime start,
                                                 @Param("end") LocalDateTime end);

    @Query("SELECT ph FROM PositionHistory ph WHERE ph.staffPosition.staff.id = :staffId " +
            "ORDER BY ph.changedAt DESC")
    Page<PositionHistory> findLatestByStaffId(@Param("staffId") Long staffId, Pageable pageable);

    @Query("SELECT ph FROM PositionHistory ph WHERE ph.staffPosition.position.id = :positionId " +
            "ORDER BY ph.changedAt DESC")
    Page<PositionHistory> findLatestByPositionId(@Param("positionId") Long positionId, Pageable pageable);

    @Query("SELECT COUNT(ph) FROM PositionHistory ph WHERE ph.action = 'ASSIGNED' AND ph.changedAt >= :date")
    Long countAssignmentsSince(@Param("date") LocalDateTime date);

    @Query("SELECT DISTINCT ph.staffPosition.staff.id FROM PositionHistory ph WHERE ph.changedAt >= :date")
    List<Long> findStaffWithChangesSince(@Param("date") LocalDateTime date);
}