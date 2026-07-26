package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.user.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

    Optional<Parent> findByUserId(Long userId);

    @Query("SELECT p FROM Parent p WHERE p.fullName LIKE %:name%")
    List<Parent> searchByName(@Param("name") String name);

    @Query("SELECT p FROM Parent p JOIN p.students s WHERE s.id = :studentId")
    List<Parent> findParentsByStudentId(@Param("studentId") Long studentId);
}