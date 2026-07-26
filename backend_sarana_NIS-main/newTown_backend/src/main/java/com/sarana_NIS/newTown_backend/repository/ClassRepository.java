package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.academic.Class;
import com.sarana_NIS.newTown_backend.model.entity.user.Student;
import com.sarana_NIS.newTown_backend.model.enums.ClassStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {

    // ========== FIND BY RELATIONSHIPS ==========

    @Query("SELECT c FROM Class c WHERE c.level.id = :levelId")
    List<Class> findByLevelId(@Param("levelId") Long levelId);

    @Query("SELECT c FROM Class c WHERE c.teacher.id = :teacherId")
    List<Class> findByTeacherId(@Param("teacherId") Long teacherId);

    @Query("SELECT c FROM Class c WHERE c.status = :status")
    List<Class> findByStatus(@Param("status") ClassStatus status);

    @Query("SELECT c FROM Class c WHERE c.level.id = :levelId AND c.status = 'ACTIVE'")
    List<Class> findActiveByLevelId(@Param("levelId") Long levelId);

    // ========== FIND WITH FETCH ==========

    @Query("SELECT c FROM Class c LEFT JOIN FETCH c.students WHERE c.id = :id")
    Optional<Class> findByIdWithStudents(@Param("id") Long id);

    @Query("SELECT c FROM Class c LEFT JOIN FETCH c.classSubjects WHERE c.id = :id")
    Optional<Class> findByIdWithSubjects(@Param("id") Long id);

    @Query("SELECT c FROM Class c LEFT JOIN FETCH c.level WHERE c.id = :id")
    Optional<Class> findByIdWithLevel(@Param("id") Long id);

    @Query("SELECT c FROM Class c LEFT JOIN FETCH c.teacher WHERE c.id = :id")
    Optional<Class> findByIdWithTeacher(@Param("id") Long id);

    // ========== COUNT METHODS ==========

    @Query("SELECT COUNT(s) FROM Student s WHERE s.currentClass.id = :classId")
    Long countStudentsByClassId(@Param("classId") Long classId);

    @Query("SELECT COUNT(c) FROM Class c WHERE c.level.id = :levelId")
    Long countByLevelId(@Param("levelId") Long levelId);

    @Query("SELECT COUNT(c) FROM Class c WHERE c.status = :status")
    Long countByStatus(@Param("status") ClassStatus status);

    @Query("SELECT COUNT(c) FROM Class c WHERE c.teacher.id = :teacherId")
    Long countByTeacherId(@Param("teacherId") Long teacherId);

    // ========== FIND STUDENTS IN CLASS ==========

    @Query("SELECT s FROM Student s WHERE s.currentClass.id = :classId")
    List<Student> findStudentsByClassId(@Param("classId") Long classId);

    @Query("SELECT s FROM Student s WHERE s.currentClass.id = :classId AND s.studentStatus = 'ACTIVE'")
    List<Student> findActiveStudentsByClassId(@Param("classId") Long classId);

    @Query("SELECT s FROM Student s WHERE s.currentClass.id = :classId ORDER BY s.nameEnglish ASC")
    List<Student> findStudentsByClassIdOrdered(@Param("classId") Long classId);

    // ========== AVAILABILITY CHECKS ==========

    @Query("SELECT c FROM Class c WHERE c.currentEnrollment < c.capacity AND c.status = 'ACTIVE'")
    List<Class> findClassesWithAvailableSpots();

    @Query("SELECT c FROM Class c WHERE c.capacity - c.currentEnrollment >= :minSpots")
    List<Class> findClassesWithMinSpots(@Param("minSpots") Integer minSpots);

    @Query("SELECT c FROM Class c WHERE c.currentEnrollment >= :threshold")
    List<Class> findOvercrowdedClasses(@Param("threshold") Integer threshold);

    @Query("SELECT c FROM Class c WHERE c.currentEnrollment < c.capacity AND c.level.id = :levelId")
    List<Class> findAvailableByLevelId(@Param("levelId") Long levelId);

    // ========== SEARCH ==========

    @Query("SELECT c FROM Class c WHERE c.name LIKE CONCAT('%', :name, '%')")
    List<Class> searchClassesByName(@Param("name") String name);

    @Query("SELECT c FROM Class c WHERE c.name LIKE CONCAT('%', :keyword, '%') " +
            "OR c.grade LIKE CONCAT('%', :keyword, '%') " +
            "OR c.room LIKE CONCAT('%', :keyword, '%')")
    Page<Class> searchClasses(@Param("keyword") String keyword, Pageable pageable);

    // ========== ACADEMIC YEAR ==========

    @Query("SELECT c FROM Class c WHERE c.academicYear = :academicYear")
    List<Class> findByAcademicYear(@Param("academicYear") String academicYear);

    @Query("SELECT c FROM Class c WHERE c.academicYear = :academicYear AND c.status = 'ACTIVE'")
    List<Class> findActiveByAcademicYear(@Param("academicYear") String academicYear);

    @Query("SELECT DISTINCT c.academicYear FROM Class c ORDER BY c.academicYear DESC")
    List<String> findDistinctAcademicYears();

    // ========== STATISTICS ==========

    @Query("SELECT AVG(c.currentEnrollment) FROM Class c")
    Double findAverageClassSize();

    @Query("SELECT AVG(c.capacity) FROM Class c")
    Double findAverageCapacity();

    @Query("SELECT MAX(c.currentEnrollment) FROM Class c")
    Integer findMaxEnrollment();

    @Query("SELECT MIN(c.currentEnrollment) FROM Class c")
    Integer findMinEnrollment();

    @Query("SELECT SUM(c.capacity) FROM Class c")
    Integer findTotalCapacity();

    @Query("SELECT SUM(c.currentEnrollment) FROM Class c")
    Integer findTotalEnrollment();

    // ========== LEVEL SPECIFIC ==========

    @Query("SELECT c.level.id, COUNT(c) FROM Class c GROUP BY c.level.id")
    List<Object[]> countClassesByLevel();

    @Query("SELECT c.level.id, SUM(c.currentEnrollment) FROM Class c GROUP BY c.level.id")
    List<Object[]> countStudentsByLevel();

    // ========== TEACHER SPECIFIC ==========

    @Query("SELECT c FROM Class c WHERE c.teacher.id = :teacherId AND c.status = 'ACTIVE'")
    List<Class> findActiveByTeacherId(@Param("teacherId") Long teacherId);

    @Query("SELECT COUNT(DISTINCT c.teacher.id) FROM Class c WHERE c.teacher IS NOT NULL")
    Long countDistinctTeachers();

    // ========== PAGEABLE QUERIES ==========

    @Query("SELECT c FROM Class c WHERE c.level.id = :levelId")
    Page<Class> findByLevelId(@Param("levelId") Long levelId, Pageable pageable);

    @Query("SELECT c FROM Class c WHERE c.status = :status")
    Page<Class> findByStatus(@Param("status") ClassStatus status, Pageable pageable);

    @Query("SELECT c FROM Class c WHERE c.academicYear = :academicYear")
    Page<Class> findByAcademicYear(@Param("academicYear") String academicYear, Pageable pageable);

    // ========== VALIDATION ==========

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Class c WHERE c.name = :name AND c.id != :id")
    boolean existsByNameExcludingId(@Param("name") String name, @Param("id") Long id);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Class c WHERE c.teacher.id = :teacherId AND c.status = 'ACTIVE'")
    boolean hasActiveClassWithTeacher(@Param("teacherId") Long teacherId);

    // ========== BULK OPERATIONS ==========

    @Query("UPDATE Class c SET c.status = :status WHERE c.id IN :classIds")
    void updateStatusForClasses(@Param("status") ClassStatus status, @Param("classIds") List<Long> classIds);

    @Query("UPDATE Class c SET c.currentEnrollment = c.currentEnrollment + :increment WHERE c.id = :classId")
    void incrementEnrollment(@Param("classId") Long classId, @Param("increment") Integer increment);

    @Query("UPDATE Class c SET c.currentEnrollment = c.currentEnrollment - :decrement WHERE c.id = :classId")
    void decrementEnrollment(@Param("classId") Long classId, @Param("decrement") Integer decrement);
}