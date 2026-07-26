package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.position.StaffPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StaffPositionRepository extends JpaRepository<StaffPosition, Long> {

    @Query("SELECT sp FROM StaffPosition sp WHERE sp.staff.id = :staffId")
    List<StaffPosition> findByStaffId(@Param("staffId") Long staffId);

    @Query("SELECT sp FROM StaffPosition sp WHERE sp.position.id = :positionId")
    List<StaffPosition> findByPositionId(@Param("positionId") Long positionId);

    @Query("SELECT sp FROM StaffPosition sp WHERE sp.staff.id = :staffId AND sp.isActive = true")
    List<StaffPosition> findActiveByStaffId(@Param("staffId") Long staffId);

    @Query("SELECT sp FROM StaffPosition sp WHERE sp.isTemporary = true AND sp.isActive = true")
    List<StaffPosition> findAllActiveTemporary();

    @Query("SELECT sp FROM StaffPosition sp WHERE sp.isTemporary = true AND sp.endDate < CURRENT_DATE")
    List<StaffPosition> findExpiredTemporary();

    @Query("SELECT sp FROM StaffPosition sp WHERE sp.isTemporary = true AND sp.endDate BETWEEN :start AND :end")
    List<StaffPosition> findTemporaryExpiringBetween(@Param("start") LocalDate start,
                                                     @Param("end") LocalDate end);

    @Query("SELECT sp FROM StaffPosition sp WHERE sp.staff.id = :staffId AND sp.position.id = :positionId")
    StaffPosition findByStaffAndPosition(@Param("staffId") Long staffId,
                                         @Param("positionId") Long positionId);

    @Query("SELECT sp FROM StaffPosition sp LEFT JOIN FETCH sp.position WHERE sp.staff.id = :staffId")
    List<StaffPosition> findByStaffIdWithPosition(@Param("staffId") Long staffId);

    @Query("SELECT sp FROM StaffPosition sp WHERE sp.isActive = true AND sp.startDate <= CURRENT_DATE")
    List<StaffPosition> findActivePositions();

    @Query("SELECT COUNT(sp) FROM StaffPosition sp WHERE sp.staff.id = :staffId AND sp.isActive = true")
    Long countActiveByStaffId(@Param("staffId") Long staffId);

    // ========== NEW QUERIES ==========

    @Query("SELECT COUNT(sp) FROM StaffPosition sp WHERE sp.position.id = :positionId")
    Long countByPositionId(@Param("positionId") Long positionId);

    @Query("SELECT COUNT(sp) FROM StaffPosition sp WHERE sp.position.id = :positionId AND sp.isActive = true")
    Long countActiveByPositionId(@Param("positionId") Long positionId);

    @Query("SELECT COUNT(sp) FROM StaffPosition sp WHERE sp.position.id = :positionId AND sp.isTemporary = true")
    Long countTemporaryByPositionId(@Param("positionId") Long positionId);

    @Query("SELECT sp FROM StaffPosition sp LEFT JOIN FETCH sp.staff WHERE sp.position.id = :positionId")
    List<StaffPosition> findByPositionIdWithStaff(@Param("positionId") Long positionId);

    @Query("SELECT sp FROM StaffPosition sp LEFT JOIN FETCH sp.staff WHERE sp.position.id = :positionId AND sp.isActive = true")
    List<StaffPosition> findActiveByPositionIdWithStaff(@Param("positionId") Long positionId);
}