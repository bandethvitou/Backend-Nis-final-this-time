package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.user.LeaveRequest;
import com.sarana_NIS.newTown_backend.model.enums.LeaveStatus;
import com.sarana_NIS.newTown_backend.model.enums.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    // Find by staff ID
    List<LeaveRequest> findByStaffId(Long staffId);

    // Find by status
    List<LeaveRequest> findByStatus(LeaveStatus status);

    // Find by staff and status
    @Query("SELECT l FROM LeaveRequest l WHERE l.staff.id = :staffId AND l.status = :status")
    List<LeaveRequest> findByStaffAndStatus(@Param("staffId") Long staffId,
                                            @Param("status") LeaveStatus status);

    // Find pending approval
    @Query("SELECT l FROM LeaveRequest l WHERE l.status = 'PENDING' ORDER BY l.createdAt ASC")
    List<LeaveRequest> findPendingApproval();

    // Find by date range
    @Query("SELECT l FROM LeaveRequest l WHERE l.startDate <= :endDate AND l.endDate >= :startDate")
    List<LeaveRequest> findByDateRange(@Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate);

    // Find by staff and date range (overlapping)
    @Query("SELECT l FROM LeaveRequest l WHERE l.staff.id = :staffId " +
            "AND l.startDate <= :endDate AND l.endDate >= :startDate " +
            "AND l.status IN ('PENDING', 'APPROVED')")
    List<LeaveRequest> findByStaffAndDateRange(@Param("staffId") Long staffId,
                                               @Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);

    // Count approved leaves by staff and type for a year
    @Query("SELECT COUNT(l) FROM LeaveRequest l WHERE l.staff.id = :staffId " +
            "AND l.leaveType = :leaveType AND l.status = 'APPROVED' " +
            "AND YEAR(l.startDate) = :year")
    Integer countApprovedLeavesByStaffAndType(@Param("staffId") Long staffId,
                                              @Param("leaveType") String leaveType,
                                              @Param("year") int year);

    // Count pending by staff and type
    @Query("SELECT COUNT(l) FROM LeaveRequest l WHERE l.staff.id = :staffId " +
            "AND l.leaveType = :leaveType AND l.status = 'PENDING' " +
            "AND YEAR(l.startDate) = :year")
    Integer countPendingByStaffAndType(@Param("staffId") Long staffId,
                                       @Param("leaveType") String leaveType,
                                       @Param("year") int year);

    // Count pending leaves
    @Query("SELECT COUNT(l) FROM LeaveRequest l WHERE l.status = 'PENDING'")
    Long countPendingLeaves();

    // Count by status
    @Query("SELECT COUNT(l) FROM LeaveRequest l WHERE l.status = :status")
    Long countByStatus(@Param("status") LeaveStatus status);

    // Count active leaves on a specific date
    @Query("SELECT COUNT(l) FROM LeaveRequest l WHERE l.startDate <= :date AND l.endDate >= :date " +
            "AND l.status = 'APPROVED'")
    Long countActiveLeavesOnDate(@Param("date") LocalDate date);

    // Find active leaves on a specific date
    @Query("SELECT l FROM LeaveRequest l WHERE l.startDate <= :date AND l.endDate >= :date " +
            "AND l.status = 'APPROVED'")
    List<LeaveRequest> findActiveLeavesOnDate(@Param("date") LocalDate date);

    // Count by leave type
    @Query("SELECT l.leaveType, COUNT(l) FROM LeaveRequest l GROUP BY l.leaveType")
    List<Object[]> countByLeaveType();

    // Count by month
    @Query("SELECT MONTH(l.startDate), COUNT(l) FROM LeaveRequest l " +
            "WHERE YEAR(l.startDate) = YEAR(CURRENT_DATE) " +
            "GROUP BY MONTH(l.startDate) ORDER BY MONTH(l.startDate)")
    List<Object[]> countByMonth();

    // Find by date range with status
    @Query("SELECT l FROM LeaveRequest l WHERE l.startDate BETWEEN :startDate AND :endDate " +
            "OR l.endDate BETWEEN :startDate AND :endDate")
    List<LeaveRequest> findLeavesInDateRange(@Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);
}