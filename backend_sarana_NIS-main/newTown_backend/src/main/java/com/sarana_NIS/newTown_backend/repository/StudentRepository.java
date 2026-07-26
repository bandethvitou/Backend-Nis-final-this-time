package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.user.Student;  // ✅ USE user.Student
import com.sarana_NIS.newTown_backend.model.enums.EnrollmentStatus;
import com.sarana_NIS.newTown_backend.model.enums.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {  // ✅ user.Student

    Optional<Student> findByStudentId(String studentId);

    @Query("SELECT s FROM Student s WHERE s.user.id = :userId")
    Optional<Student> findByUserId(@Param("userId") Long userId);

    @Query("SELECT s FROM Student s WHERE s.currentClass.id = :classId")
    List<Student> findByCurrentClassId(@Param("classId") Long classId);

    @Query("SELECT s FROM Student s WHERE s.id IN (SELECT sp.student.id FROM StudentParent sp WHERE sp.parent.id = :parentId)")
    List<Student> findStudentsByParentId(@Param("parentId") Long parentId);

    List<Student> findByLevel(Level level);

    List<Student> findByStudentStatus(EnrollmentStatus status);

    @Query("SELECT s FROM Student s WHERE s.level = :level AND s.studentStatus = :status")
    List<Student> findByLevelAndStatus(@Param("level") Level level,
                                       @Param("status") EnrollmentStatus status);

    @Query("SELECT s FROM Student s WHERE s.nameEnglish LIKE %:keyword% OR s.nameKhmer LIKE %:keyword% OR s.studentId LIKE %:keyword%")
    List<Student> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.currentClass.id = :classId")
    Long countByCurrentClassId(@Param("classId") Long classId);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.studentStatus = :status")
    Long countByStatus(@Param("status") EnrollmentStatus status);

    @Query("SELECT s FROM Student s WHERE s.currentClass.id = :classId AND s.studentStatus = 'ACTIVE'")
    List<Student> findActiveByClassId(@Param("classId") Long classId);
}