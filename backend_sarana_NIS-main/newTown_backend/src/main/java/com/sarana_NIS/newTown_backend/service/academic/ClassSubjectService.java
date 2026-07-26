package com.sarana_NIS.newTown_backend.service.academic;

import com.sarana_NIS.newTown_backend.dto.request.academic.ClassSubjectRequest;
import com.sarana_NIS.newTown_backend.dto.response.academic.ClassSubjectResponse;
import com.sarana_NIS.newTown_backend.model.entity.academic.Class;
import com.sarana_NIS.newTown_backend.model.entity.academic.ClassSubject;
import com.sarana_NIS.newTown_backend.model.entity.academic.Subject;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.repository.ClassRepository;
import com.sarana_NIS.newTown_backend.repository.ClassSubjectRepository;
import com.sarana_NIS.newTown_backend.repository.SubjectRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassSubjectService {

    private static final Logger log = LoggerFactory.getLogger(ClassSubjectService.class);

    private final ClassSubjectRepository classSubjectRepository;
    private final ClassRepository classRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public ClassSubjectService(ClassSubjectRepository classSubjectRepository, ClassRepository classRepository,
                               SubjectRepository subjectRepository, UserRepository userRepository) {
        this.classSubjectRepository = classSubjectRepository;
        this.classRepository = classRepository;
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ClassSubjectResponse assignSubject(ClassSubjectRequest request) {
        Class classEntity = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));
        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        classSubjectRepository.findByClassAndSubject(request.getClassId(), request.getSubjectId())
                .ifPresent(cs -> {
                    throw new RuntimeException("Subject already assigned to this class");
                });

        ClassSubject classSubject = new ClassSubject();
        classSubject.setClassEntity(classEntity);
        classSubject.setSubject(subject);
        classSubject.setAcademicYear(request.getAcademicYear());
        classSubject.setTerm(request.getTerm());
        classSubject.setSchedule(request.getSchedule());

        if (request.getTeacherId() != null) {
            User teacher = userRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            classSubject.setTeacher(teacher);
        }

        ClassSubject saved = classSubjectRepository.save(classSubject);
        log.info("Subject '{}' assigned to class '{}'", subject.getName(), classEntity.getName());
        return mapToResponse(saved);
    }

    public List<ClassSubjectResponse> getSubjectsByClass(Long classId) {
        List<ClassSubject> classSubjects = classSubjectRepository.findByClassId(classId);
        return classSubjects.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<ClassSubjectResponse> getSubjectsByTeacher(Long teacherId) {
        List<ClassSubject> classSubjects = classSubjectRepository.findByTeacherId(teacherId);
        return classSubjects.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional
    public void removeSubject(Long classSubjectId) {
        ClassSubject classSubject = classSubjectRepository.findById(classSubjectId)
                .orElseThrow(() -> new RuntimeException("Class subject not found"));
        classSubjectRepository.delete(classSubject);
        log.info("Subject removed from class");
    }

    private ClassSubjectResponse mapToResponse(ClassSubject classSubject) {
        ClassSubjectResponse response = new ClassSubjectResponse();
        response.setId(classSubject.getId());
        response.setClassName(classSubject.getClassEntity().getName());
        response.setSubjectName(classSubject.getSubject().getName());
        response.setAcademicYear(classSubject.getAcademicYear());
        response.setTerm(classSubject.getTerm());
        response.setTeacherName(classSubject.getTeacher() != null ? classSubject.getTeacher().getFullName() : null);
        response.setSchedule(classSubject.getSchedule());
        return response;
    }
}