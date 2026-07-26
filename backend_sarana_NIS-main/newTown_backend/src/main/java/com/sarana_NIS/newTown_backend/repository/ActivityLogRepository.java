package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.management.ActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

    @Query("SELECT al FROM ActivityLog al WHERE al.user.id = :userId")
    List<ActivityLog> findByUserId(@Param("userId") Long userId);

    @Query("SELECT al FROM ActivityLog al WHERE al.action = :action")
    List<ActivityLog> findByAction(@Param("action") String action);

    @Query("SELECT al FROM ActivityLog al WHERE al.entityType = :entityType")
    List<ActivityLog> findByEntityType(@Param("entityType") String entityType);

    @Query("SELECT al FROM ActivityLog al WHERE al.entityType = :entityType AND al.entityId = :entityId")
    List<ActivityLog> findByEntityTypeAndEntityId(@Param("entityType") String entityType,
                                                  @Param("entityId") Long entityId);

    @Query("SELECT al FROM ActivityLog al WHERE al.createdAt BETWEEN :start AND :end")
    Page<ActivityLog> findByCreatedAtBetween(@Param("start") LocalDateTime start,
                                             @Param("end") LocalDateTime end,
                                             Pageable pageable);

    @Query("SELECT al FROM ActivityLog al WHERE al.user.id = :userId AND al.createdAt BETWEEN :start AND :end")
    Page<ActivityLog> findByUserAndDateRange(@Param("userId") Long userId,
                                             @Param("start") LocalDateTime start,
                                             @Param("end") LocalDateTime end,
                                             Pageable pageable);

    @Query("SELECT al FROM ActivityLog al WHERE al.ipAddress = :ipAddress")
    List<ActivityLog> findByIpAddress(@Param("ipAddress") String ipAddress);

    @Query("SELECT COUNT(al) FROM ActivityLog al WHERE al.user.id = :userId AND al.createdAt >= :date")
    Long countByUserSince(@Param("userId") Long userId,
                          @Param("date") LocalDateTime date);

    @Query("SELECT al.user.id, COUNT(al) FROM ActivityLog al WHERE al.createdAt BETWEEN :start AND :end GROUP BY al.user.id")
    List<Object[]> findActivityCountByUser(@Param("start") LocalDateTime start,
                                           @Param("end") LocalDateTime end);

    @Query("SELECT al.action, COUNT(al) FROM ActivityLog al WHERE al.createdAt BETWEEN :start AND :end GROUP BY al.action")
    List<Object[]> findActivityCountByAction(@Param("start") LocalDateTime start,
                                             @Param("end") LocalDateTime end);

    @Query("SELECT al FROM ActivityLog al WHERE al.user.id = :userId AND al.action = :action")
    List<ActivityLog> findByUserAndAction(@Param("userId") Long userId,
                                          @Param("action") String action);

    @Query("SELECT al FROM ActivityLog al ORDER BY al.createdAt DESC")
    Page<ActivityLog> findLatest(Pageable pageable);
}