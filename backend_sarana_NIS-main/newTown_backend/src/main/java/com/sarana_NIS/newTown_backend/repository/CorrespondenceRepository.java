package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.communication.Correspondence;
import com.sarana_NIS.newTown_backend.model.enums.CorrespondenceFormat;
import com.sarana_NIS.newTown_backend.model.enums.CorrespondenceStatus;
import com.sarana_NIS.newTown_backend.model.enums.CorrespondenceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CorrespondenceRepository extends JpaRepository<Correspondence, Long> {

    @Query("SELECT c FROM Correspondence c WHERE c.type = :type")
    List<Correspondence> findByType(@Param("type") CorrespondenceType type);

    @Query("SELECT c FROM Correspondence c WHERE c.format = :format")
    List<Correspondence> findByFormat(@Param("format") CorrespondenceFormat format);

    @Query("SELECT c FROM Correspondence c WHERE c.status = :status")
    List<Correspondence> findByStatus(@Param("status") CorrespondenceStatus status);

    @Query("SELECT c FROM Correspondence c WHERE c.date = :date")
    List<Correspondence> findByDate(@Param("date") LocalDate date);

    @Query("SELECT c FROM Correspondence c WHERE c.date BETWEEN :startDate AND :endDate")
    List<Correspondence> findByDateBetween(@Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);

    @Query("SELECT c FROM Correspondence c WHERE c.fromTo LIKE CONCAT('%', :keyword, '%') " +
            "OR c.subject LIKE CONCAT('%', :keyword, '%')")
    List<Correspondence> searchCorrespondence(@Param("keyword") String keyword);

    // ❌ DELETE THIS METHOD - Correspondence doesn't have createdBy field
    // @Query("SELECT c FROM Correspondence c WHERE c.createdBy = :createdBy")
    // List<Correspondence> findByCreatedBy(@Param("createdBy") Long createdBy);

    @Query("SELECT c FROM Correspondence c WHERE c.status = 'PENDING'")
    List<Correspondence> findPendingCorrespondence();

    @Query("SELECT c FROM Correspondence c WHERE c.priority = 'HIGH' AND c.status = 'PENDING'")
    List<Correspondence> findHighPriorityPending();

    @Query("SELECT COUNT(c) FROM Correspondence c WHERE c.status = 'PENDING'")
    Long countPendingCorrespondence();

    @Query("SELECT c.type, COUNT(c) FROM Correspondence c GROUP BY c.type")
    List<Object[]> countCorrespondenceByType();
}