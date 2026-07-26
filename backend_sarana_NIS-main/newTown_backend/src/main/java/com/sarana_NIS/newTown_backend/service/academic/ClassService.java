package com.sarana_NIS.newTown_backend.service.academic;

import com.sarana_NIS.newTown_backend.dto.request.academic.ClassRequest;
import com.sarana_NIS.newTown_backend.dto.response.academic.ClassResponse;
import com.sarana_NIS.newTown_backend.dto.response.academic.ClassEnrollmentResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.model.entity.academic.AcademicLevel;
import com.sarana_NIS.newTown_backend.model.entity.academic.Class;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.enums.ClassStatus;
import com.sarana_NIS.newTown_backend.repository.AcademicLevelRepository;
import com.sarana_NIS.newTown_backend.repository.ClassRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassService {

    private static final Logger log = LoggerFactory.getLogger(ClassService.class);

    private final ClassRepository classRepository;
    private final AcademicLevelRepository academicLevelRepository;
    private final UserRepository userRepository;

    public ClassService(ClassRepository classRepository, AcademicLevelRepository academicLevelRepository, UserRepository userRepository) {
        this.classRepository = classRepository;
        this.academicLevelRepository = academicLevelRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ClassResponse createClass(ClassRequest request) {
        AcademicLevel level = academicLevelRepository.findById(request.getLevelId())
                .orElseThrow(() -> new RuntimeException("Academic level not found"));

        Class classEntity = new Class();
        classEntity.setName(request.getName());
        classEntity.setLevel(level);
        classEntity.setGrade(request.getGrade());
        classEntity.setAcademicYear(request.getAcademicYear());
        classEntity.setRoom(request.getRoom());
        classEntity.setCapacity(request.getCapacity());
        classEntity.setSchedule(request.getSchedule());
        classEntity.setStatus(ClassStatus.ACTIVE);
        classEntity.setCurrentEnrollment(0);

        if (request.getTeacherId() != null) {
            User teacher = userRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            classEntity.setTeacher(teacher);
        }

        Class saved = classRepository.save(classEntity);
        log.info("Class created: {}", saved.getName());
        return mapToResponse(saved);
    }

    @Transactional
    public ClassResponse updateClass(Long classId, ClassRequest request) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        if (request.getName() != null) classEntity.setName(request.getName());
        if (request.getLevelId() != null) {
            AcademicLevel level = academicLevelRepository.findById(request.getLevelId())
                    .orElseThrow(() -> new RuntimeException("Academic level not found"));
            classEntity.setLevel(level);
        }
        if (request.getGrade() != null) classEntity.setGrade(request.getGrade());
        if (request.getAcademicYear() != null) classEntity.setAcademicYear(request.getAcademicYear());
        if (request.getRoom() != null) classEntity.setRoom(request.getRoom());
        if (request.getCapacity() != null) classEntity.setCapacity(request.getCapacity());
        if (request.getSchedule() != null) classEntity.setSchedule(request.getSchedule());
        if (request.getTeacherId() != null) {
            User teacher = userRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            classEntity.setTeacher(teacher);
        }

        Class updated = classRepository.save(classEntity);
        log.info("Class updated: {}", updated.getName());
        return mapToResponse(updated);
    }

    public ClassResponse getClassById(Long classId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        return mapToResponse(classEntity);
    }

    public PageResponse<ClassResponse> getAllClasses(Pageable pageable) {
        Page<Class> page = classRepository.findAll(pageable);
        List<ClassResponse> content = page.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(content, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

    public List<ClassResponse> getClassesByLevel(Long levelId) {
        List<Class> classes = classRepository.findByLevelId(levelId);
        return classes.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<ClassResponse> getActiveClassesByLevel(Long levelId) {
        List<Class> classes = classRepository.findActiveByLevelId(levelId);
        return classes.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public ClassEnrollmentResponse getClassEnrollment(Long classId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        ClassEnrollmentResponse response = new ClassEnrollmentResponse();
        response.setClassId(classEntity.getId());
        response.setClassName(classEntity.getName());
        response.setLevel(classEntity.getLevel().getName());
        response.setGrade(classEntity.getGrade());
        response.setTeacherName(classEntity.getTeacher() != null ? classEntity.getTeacher().getFullName() : null);
        response.setRoom(classEntity.getRoom());
        response.setCapacity(classEntity.getCapacity());
        response.setCurrentEnrollment(classEntity.getCurrentEnrollment());
        response.setAvailableSpots(classEntity.getCapacity() - classEntity.getCurrentEnrollment());
        response.setOccupancyRate(classEntity.getCapacity() > 0 ? (double) classEntity.getCurrentEnrollment() / classEntity.getCapacity() * 100 : 0);
        response.setStatus(classEntity.getCurrentEnrollment() >= classEntity.getCapacity() ? "FULL" : "OPEN");

        List<ClassEnrollmentResponse.StudentEnrollment> students = classRepository.findStudentsByClassId(classId)
                .stream()
                .map(student -> {
                    ClassEnrollmentResponse.StudentEnrollment se = new ClassEnrollmentResponse.StudentEnrollment();
                    se.setStudentId(student.getId());
                    se.setStudentName(student.getNameEnglish());
                    se.setGender(student.getGender() != null ? student.getGender().name() : null);
                    se.setEnrollmentDate(student.getEnrollmentDate());
                    se.setStatus(student.getStudentStatus() != null ? student.getStudentStatus().name() : null);
                    return se;
                })
                .collect(Collectors.toList());
        response.setStudents(students);
        return response;
    }

    @Transactional
    public void deleteClass(Long classId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        classEntity.setStatus(ClassStatus.INACTIVE);
        classRepository.save(classEntity);
        log.info("Class deactivated: {}", classEntity.getName());
    }

    private ClassResponse mapToResponse(Class classEntity) {
        ClassResponse response = new ClassResponse();
        response.setId(classEntity.getId());
        response.setName(classEntity.getName());
        if (classEntity.getLevel() != null) {
            response.setLevelName(classEntity.getLevel().getName());
        }
        response.setGrade(classEntity.getGrade());
        response.setAcademicYear(classEntity.getAcademicYear());
        response.setRoom(classEntity.getRoom());
        response.setCapacity(classEntity.getCapacity());
        response.setCurrentEnrollment(classEntity.getCurrentEnrollment());
        response.setStatus(classEntity.getStatus() != null ? classEntity.getStatus().name() : null);
        if (classEntity.getTeacher() != null) {
            response.setTeacherName(classEntity.getTeacher().getFullName());
            response.setTeacherId(classEntity.getTeacher().getId());
        }
        response.setSchedule(classEntity.getSchedule());
        response.setCreatedAt(classEntity.getCreatedAt());
        return response;
    }
}