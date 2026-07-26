package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.management.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByName(String name);

    boolean existsByName(String name);

    Optional<Department> findByDepartmentCode(String departmentCode);

    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.staff WHERE d.id = :id")
    Optional<Department> findByIdWithStaff(@Param("id") Long id);

    @Query("SELECT d.id, d.name, COUNT(s) FROM Department d LEFT JOIN d.staff s GROUP BY d.id, d.name")
    List<Object[]> findDepartmentsWithStaffCount();

    @Query("SELECT COUNT(s) FROM Staff s WHERE s.department.id = :departmentId")
    long countStaffByDepartmentId(@Param("departmentId") Long departmentId);
}