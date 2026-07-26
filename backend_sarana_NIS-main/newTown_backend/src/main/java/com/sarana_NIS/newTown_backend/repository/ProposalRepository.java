package com.sarana_NIS.newTown_backend.repository.management;

import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.management.Department;
import com.sarana_NIS.newTown_backend.model.entity.management.Proposal;
import com.sarana_NIS.newTown_backend.model.enums.ProposalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    // Find by status
    List<Proposal> findByStatus(ProposalStatus status);

    // Find by submitted by user
    List<Proposal> findBySubmittedBy(User user);

    // Find by department
    List<Proposal> findByDepartment(Department department);

    // Count by status
    @Query("SELECT COUNT(p) FROM Proposal p WHERE p.status = :status")
    Long countByStatus(@Param("status") ProposalStatus status);

    // Find by status and department
    @Query("SELECT p FROM Proposal p WHERE p.status = :status AND p.department.id = :departmentId")
    List<Proposal> findByStatusAndDepartmentId(@Param("status") ProposalStatus status,
                                               @Param("departmentId") Long departmentId);

    // Find by submitted by user and status
    @Query("SELECT p FROM Proposal p WHERE p.submittedBy.id = :userId AND p.status = :status")
    List<Proposal> findBySubmittedByAndStatus(@Param("userId") Long userId,
                                              @Param("status") ProposalStatus status);

    // Find by date range
    @Query("SELECT p FROM Proposal p WHERE p.submittedDate BETWEEN :startDate AND :endDate")
    List<Proposal> findBySubmittedDateBetween(@Param("startDate") LocalDateTime startDate,
                                              @Param("endDate") LocalDateTime endDate);

    // Count by department
    @Query("SELECT COUNT(p) FROM Proposal p WHERE p.department.id = :departmentId")
    Long countByDepartmentId(@Param("departmentId") Long departmentId);

    // Find escalated proposals
    @Query("SELECT p FROM Proposal p WHERE p.isEscalated = true OR p.status = 'ESCALATED'")
    List<Proposal> findEscalatedProposals();

    // Find pending proposals with due date
    @Query("SELECT p FROM Proposal p WHERE p.status = 'PENDING' AND p.dueDate < CURRENT_TIMESTAMP")
    List<Proposal> findOverduePendingProposals();
}