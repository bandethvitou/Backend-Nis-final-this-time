package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.hr.HiringRequest;
import com.sarana_NIS.newTown_backend.model.enums.Priority;
import com.sarana_NIS.newTown_backend.model.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HiringRequestRepository extends JpaRepository<HiringRequest, Long> {

    @Query("SELECT h FROM HiringRequest h WHERE h.department.id = :departmentId")
    List<HiringRequest> findByDepartmentId(@Param("departmentId") Long departmentId);

    @Query("SELECT h FROM HiringRequest h WHERE h.status = :status")
    List<HiringRequest> findByStatus(@Param("status") RequestStatus status);

    @Query("SELECT h FROM HiringRequest h WHERE h.priority = :priority")
    List<HiringRequest> findByPriority(@Param("priority") Priority priority);

    @Query("SELECT h FROM HiringRequest h WHERE h.position LIKE CONCAT('%', :position, '%')")
    List<HiringRequest> findByPositionContaining(@Param("position") String position);

    @Query("SELECT h FROM HiringRequest h WHERE h.approvedBy IS NULL AND h.status = 'PENDING'")
    List<HiringRequest> findPendingApproval();

    @Query("SELECT COUNT(h) FROM HiringRequest h WHERE h.status = 'PENDING'")
    Long countPendingRequests();

    @Query("SELECT h.department.id, COUNT(h) FROM HiringRequest h WHERE h.status = 'APPROVED' GROUP BY h.department.id")
    List<Object[]> findApprovedRequestsByDepartment();

    @Query("SELECT SUM(h.numberOfPositions) FROM HiringRequest h WHERE h.status = 'APPROVED'")
    Long sumApprovedPositions();

    @Query("SELECT h FROM HiringRequest h WHERE h.status = 'PENDING' ORDER BY h.priority DESC")
    List<HiringRequest> findPendingOrderedByPriority();

    @Query("SELECT h FROM HiringRequest h WHERE h.status = 'PENDING' AND h.priority = 'HIGH'")
    List<HiringRequest> findHighPriorityPending();
}