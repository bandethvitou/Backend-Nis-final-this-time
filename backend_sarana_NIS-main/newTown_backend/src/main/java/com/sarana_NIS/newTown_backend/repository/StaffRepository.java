package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.user.Staff;
import com.sarana_NIS.newTown_backend.model.enums.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    Optional<Staff> findByStaffId(String staffId);

    @Query("SELECT s FROM Staff s WHERE s.user.id = :userId")
    Optional<Staff> findByUserId(@Param("userId") Long userId);

    List<Staff> findByDepartmentId(Long departmentId);

    List<Staff> findByStaffStatus(EmployeeStatus status);

    @Query("SELECT s FROM Staff s WHERE s.staffStatus = 'ACTIVE'")
    List<Staff> findAllActiveStaff();

    @Query("SELECT s FROM Staff s WHERE s.department.id = :departmentId AND s.staffStatus = :status")
    List<Staff> findByDepartmentAndStatus(@Param("departmentId") Long departmentId,
                                          @Param("status") EmployeeStatus status);

    @Query("SELECT s FROM Staff s WHERE s.position LIKE %:position%")
    List<Staff> findByPositionContaining(@Param("position") String position);

    @Query("SELECT COUNT(s) FROM Staff s WHERE s.department.id = :departmentId")
    Long countByDepartmentId(@Param("departmentId") Long departmentId);

    @Query("SELECT COUNT(s) FROM Staff s WHERE s.staffStatus = :status")
    Long countByStatus(@Param("status") EmployeeStatus status);

    // ✅ Add this method
    default long countActiveStaff() {
        return countByStatus(EmployeeStatus.ACTIVE);
    }
}