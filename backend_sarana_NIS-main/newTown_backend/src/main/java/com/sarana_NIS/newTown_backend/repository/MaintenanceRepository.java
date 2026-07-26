package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.it.Maintenance;
import com.sarana_NIS.newTown_backend.model.enums.MaintenanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    @Query("SELECT m FROM Maintenance m WHERE m.equipment.id = :equipmentId")
    List<Maintenance> findByEquipmentId(@Param("equipmentId") Long equipmentId);

    @Query("SELECT m FROM Maintenance m WHERE m.status = :status")
    List<Maintenance> findByStatus(@Param("status") MaintenanceStatus status);

    @Query("SELECT m FROM Maintenance m WHERE m.maintenanceDate BETWEEN :startDate AND :endDate")
    List<Maintenance> findByMaintenanceDateBetween(@Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate);

    @Query("SELECT m FROM Maintenance m WHERE m.status = 'SCHEDULED' OR m.status = 'IN_PROGRESS'")
    List<Maintenance> findActiveMaintenance();

    @Query("SELECT m FROM Maintenance m WHERE m.equipment.id = :equipmentId ORDER BY m.maintenanceDate DESC")
    List<Maintenance> findLatestByEquipmentId(@Param("equipmentId") Long equipmentId);

    @Query("SELECT COUNT(m) FROM Maintenance m WHERE m.status = 'COMPLETED'")
    Long countCompletedMaintenance();

    @Query("SELECT COUNT(m) FROM Maintenance m WHERE m.status = 'SCHEDULED'")
    Long countScheduledMaintenance();

    @Query("SELECT COUNT(m) FROM Maintenance m WHERE m.status = 'IN_PROGRESS'")
    Long countInProgressMaintenance();

    @Query("SELECT m.equipment.id, COUNT(m) FROM Maintenance m " +
            "WHERE m.status = 'COMPLETED' GROUP BY m.equipment.id ORDER BY COUNT(m) DESC")
    List<Object[]> findEquipmentWithMostMaintenance();

    @Query("SELECT m FROM Maintenance m WHERE m.maintenanceDate < :date AND m.status != 'COMPLETED'")
    List<Maintenance> findOverdueMaintenance(@Param("date") LocalDate date);

    @Query("SELECT SUM(m.cost) FROM Maintenance m WHERE m.status = 'COMPLETED' AND m.maintenanceDate >= :date")
    Double sumCompletedCostSince(@Param("date") LocalDate date);

    @Query("SELECT AVG(m.cost) FROM Maintenance m WHERE m.status = 'COMPLETED'")
    Double findAverageMaintenanceCost();
}