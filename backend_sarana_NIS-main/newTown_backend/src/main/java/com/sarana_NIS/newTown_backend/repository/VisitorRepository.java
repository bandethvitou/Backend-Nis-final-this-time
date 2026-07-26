package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.admissions.Visitor;
import com.sarana_NIS.newTown_backend.model.enums.VisitorStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    Optional<Visitor> findByPassNumber(String passNumber);

    @Query("SELECT v FROM Visitor v WHERE v.status = :status")
    List<Visitor> findByStatus(@Param("status") VisitorStatus status);

    @Query("SELECT v FROM Visitor v WHERE v.host.id = :hostId")
    List<Visitor> findByHostId(@Param("hostId") Long hostId);

    @Query("SELECT v FROM Visitor v WHERE v.checkIn BETWEEN :start AND :end")
    List<Visitor> findByCheckInBetween(@Param("start") LocalDateTime start,
                                       @Param("end") LocalDateTime end);

    @Query("SELECT v FROM Visitor v WHERE v.status = 'ACTIVE'")
    List<Visitor> findActiveVisitors();

    @Query("SELECT v FROM Visitor v WHERE v.checkOut IS NULL AND v.checkIn < :time")
    List<Visitor> findVisitorsWithNoCheckout(@Param("time") LocalDateTime time);

    @Query("SELECT v FROM Visitor v WHERE v.name LIKE CONCAT('%', :name, '%')")
    List<Visitor> searchVisitors(@Param("name") String name);

    @Query("SELECT COUNT(v) FROM Visitor v WHERE v.checkIn BETWEEN :start AND :end")
    Long countVisitorsBetween(@Param("start") LocalDateTime start,
                              @Param("end") LocalDateTime end);

    @Query("SELECT v FROM Visitor v WHERE v.checkIn >= :date AND v.status = 'ACTIVE'")
    List<Visitor> findActiveVisitorsSince(@Param("date") LocalDateTime date);

    @Query("SELECT COUNT(v) FROM Visitor v WHERE v.status = 'ACTIVE'")
    Long countActiveVisitors();

    @Query("SELECT v.host.id, COUNT(v) FROM Visitor v WHERE v.checkIn >= :date GROUP BY v.host.id")
    List<Object[]> countVisitorsByHostSince(@Param("date") LocalDateTime date);

    @Query("SELECT v.organization, COUNT(v) FROM Visitor v GROUP BY v.organization")
    List<Object[]> countVisitorsByOrganization();

    @Query("SELECT v FROM Visitor v WHERE v.purpose LIKE CONCAT('%', :purpose, '%')")
    List<Visitor> findByPurposeContaining(@Param("purpose") String purpose);
}