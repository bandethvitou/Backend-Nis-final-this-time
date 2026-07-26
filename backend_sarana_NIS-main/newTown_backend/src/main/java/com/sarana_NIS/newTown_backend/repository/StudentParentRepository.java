package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.user.StudentParent;
import com.sarana_NIS.newTown_backend.model.entity.user.StudentParent.StudentParentId;
import com.sarana_NIS.newTown_backend.model.enums.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentParentRepository extends JpaRepository<StudentParent, StudentParentId> {

    @Query("SELECT sp FROM StudentParent sp WHERE sp.student.id = :studentId")
    List<StudentParent> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT sp FROM StudentParent sp WHERE sp.parent.id = :parentId")
    List<StudentParent> findByParentId(@Param("parentId") Long parentId);

    @Query("SELECT sp FROM StudentParent sp WHERE sp.student.id = :studentId AND sp.parent.id = :parentId")
    StudentParent findByStudentAndParent(@Param("studentId") Long studentId,
                                         @Param("parentId") Long parentId);

    @Query("SELECT sp FROM StudentParent sp WHERE sp.relationship = :relationship")
    List<StudentParent> findByRelationship(@Param("relationship") Relationship relationship);

    @Query("SELECT COUNT(sp) FROM StudentParent sp WHERE sp.student.id = :studentId")
    Long countByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT COUNT(sp) FROM StudentParent sp WHERE sp.parent.id = :parentId")
    Long countByParentId(@Param("parentId") Long parentId);

    @Query("DELETE FROM StudentParent sp WHERE sp.student.id = :studentId AND sp.parent.id = :parentId")
    void deleteByStudentAndParent(@Param("studentId") Long studentId,
                                  @Param("parentId") Long parentId);
}