package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.academic.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Optional<Subject> findByCode(String code);

    List<Subject> findByDepartmentId(Long departmentId);

    @Query("SELECT s FROM Subject s WHERE s.isActive = true")
    List<Subject> findActiveSubjects();

    @Query("SELECT s FROM Subject s WHERE s.department.id = :departmentId AND s.isActive = true")
    List<Subject> findActiveByDepartmentId(@Param("departmentId") Long departmentId);

    @Query("SELECT COUNT(s) FROM Subject s WHERE s.department.id = :departmentId")
    Long countByDepartmentId(@Param("departmentId") Long departmentId);

    boolean existsByCode(String code);
}