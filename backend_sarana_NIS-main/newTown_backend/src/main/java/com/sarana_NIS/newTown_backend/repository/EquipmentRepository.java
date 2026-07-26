package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.it.Equipment;
import com.sarana_NIS.newTown_backend.model.enums.EquipmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    Optional<Equipment> findBySerialNumber(String serialNumber);

    List<Equipment> findByStatus(EquipmentStatus status);

    List<Equipment> findByCategory(String category);

    List<Equipment> findByLocation(String location);

    @Query("SELECT e FROM Equipment e WHERE e.warrantyExpiry < :date")
    List<Equipment> findExpiredWarranty(@Param("date") LocalDate date);

    @Query("SELECT e FROM Equipment e WHERE e.warrantyExpiry BETWEEN :start AND :end")
    List<Equipment> findWarrantyExpiringBetween(@Param("start") LocalDate start,
                                                @Param("end") LocalDate end);

    @Query("SELECT e FROM Equipment e WHERE e.name LIKE %:keyword% OR e.serialNumber LIKE %:keyword% OR e.category LIKE %:keyword%")
    Page<Equipment> searchEquipment(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT e.category, COUNT(e) FROM Equipment e GROUP BY e.category")
    List<Object[]> countEquipmentByCategory();

    @Query("SELECT COUNT(e) FROM Equipment e WHERE e.status = :status")
    Long countByStatus(@Param("status") EquipmentStatus status);

    @Query("SELECT e FROM Equipment e WHERE e.status = 'FUNCTIONAL'")
    List<Equipment> findFunctionalEquipment();

    @Query("SELECT e FROM Equipment e WHERE e.status = 'UNDER_REPAIR'")
    List<Equipment> findUnderRepairEquipment();

    // ✅ FIXED: Add @Query annotation
    @Query("SELECT COUNT(e) FROM Equipment e WHERE e.status = 'FUNCTIONAL'")
    long countFunctionalEquipment();

    // ✅ FIXED: Add @Query annotation
    @Query("SELECT COUNT(e) FROM Equipment e WHERE e.status = 'UNDER_REPAIR'")
    long countUnderRepairEquipment();
}