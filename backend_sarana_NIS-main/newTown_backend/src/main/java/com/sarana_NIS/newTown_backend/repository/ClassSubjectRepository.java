package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.academic.ClassSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassSubjectRepository extends JpaRepository<ClassSubject, Long> {

    @Query("SELECT cs FROM ClassSubject cs WHERE cs.classEntity.id = :classId")
    List<ClassSubject> findByClassId(@Param("classId") Long classId);

    @Query("SELECT cs FROM ClassSubject cs WHERE cs.subject.id = :subjectId")
    List<ClassSubject> findBySubjectId(@Param("subjectId") Long subjectId);

    @Query("SELECT cs FROM ClassSubject cs WHERE cs.teacher.id = :teacherId")
    List<ClassSubject> findByTeacherId(@Param("teacherId") Long teacherId);

    @Query("SELECT cs FROM ClassSubject cs WHERE cs.classEntity.id = :classId AND cs.teacher.id = :teacherId")
    List<ClassSubject> findByClassAndTeacher(@Param("classId") Long classId,
                                             @Param("teacherId") Long teacherId);

    @Query("SELECT cs FROM ClassSubject cs WHERE cs.classEntity.id = :classId AND cs.subject.id = :subjectId")
    Optional<ClassSubject> findByClassAndSubject(@Param("classId") Long classId,
                                                 @Param("subjectId") Long subjectId);

    @Query("SELECT cs FROM ClassSubject cs WHERE cs.teacher.id = :teacherId AND cs.term = :term")
    List<ClassSubject> findByTeacherAndTerm(@Param("teacherId") Long teacherId,
                                            @Param("term") String term);

    @Query("SELECT cs FROM ClassSubject cs LEFT JOIN FETCH cs.teacher WHERE cs.classEntity.id = :classId")
    List<ClassSubject> findByClassIdWithTeacher(@Param("classId") Long classId);

    @Query("SELECT COUNT(cs) FROM ClassSubject cs WHERE cs.classEntity.id = :classId")
    Long countByClassId(@Param("classId") Long classId);

    @Query("SELECT cs FROM ClassSubject cs WHERE cs.academicYear = :academicYear")
    List<ClassSubject> findByAcademicYear(@Param("academicYear") String academicYear);
}