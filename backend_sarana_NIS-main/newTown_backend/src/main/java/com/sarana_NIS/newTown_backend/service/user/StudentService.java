package com.sarana_NIS.newTown_backend.service.user;

import com.sarana_NIS.newTown_backend.dto.request.user.StudentCreateRequest;
import com.sarana_NIS.newTown_backend.dto.request.user.StudentUpdateRequest;
import com.sarana_NIS.newTown_backend.dto.request.user.StudentBulkTransferRequest;
import com.sarana_NIS.newTown_backend.dto.response.user.StudentResponse;
import com.sarana_NIS.newTown_backend.dto.response.user.StudentClassTransferResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.model.entity.academic.Class;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.user.Parent;
import com.sarana_NIS.newTown_backend.model.entity.user.Student;
import com.sarana_NIS.newTown_backend.model.enums.EnrollmentStatus;
import com.sarana_NIS.newTown_backend.repository.ClassRepository;
import com.sarana_NIS.newTown_backend.repository.ParentRepository;
import com.sarana_NIS.newTown_backend.repository.StudentRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final ParentRepository parentRepository;

    public StudentService(StudentRepository studentRepository, UserRepository userRepository,
                          ClassRepository classRepository, ParentRepository parentRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.classRepository = classRepository;
        this.parentRepository = parentRepository;
    }

    // ============================================================
    // CREATE
    // ============================================================

    @Transactional
    public StudentResponse createStudent(StudentCreateRequest request) {
        if (studentRepository.findByStudentId(request.getStudentId()).isPresent()) {
            throw new RuntimeException("Student ID already exists: " + request.getStudentId());
        }

        Student student = new Student();
        student.setStudentId(request.getStudentId());
        student.setNameKhmer(request.getNameKhmer());
        student.setNameEnglish(request.getNameEnglish());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGender(request.getGender());
        student.setLevel(request.getLevel());
        student.setPreviousSchool(request.getPreviousSchool());
        student.setMedicalNotes(request.getMedicalNotes());
        student.setStudentStatus(EnrollmentStatus.ACTIVE);
        student.setEnrollmentDate(LocalDate.now());

        if (request.getClassId() != null) {
            Class classEntity = classRepository.findById(request.getClassId())
                    .orElseThrow(() -> new RuntimeException("Class not found"));
            student.setCurrentClass(classEntity);
            classEntity.setCurrentEnrollment(classEntity.getCurrentEnrollment() + 1);
            classRepository.save(classEntity);
        }

        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            student.setUser(user);
        }

        // ✅ Now works because Student has addParent() method
        if (request.getParentIds() != null && !request.getParentIds().isEmpty()) {
            List<Parent> parents = parentRepository.findAllById(request.getParentIds());
            for (Parent parent : parents) {
                student.addParent(parent);
            }
        }

        Student savedStudent = studentRepository.save(student);
        log.info("Student created: {} ({})", savedStudent.getNameEnglish(), savedStudent.getStudentId());
        return mapToResponse(savedStudent);
    }

    // ============================================================
    // UPDATE
    // ============================================================

    @Transactional
    public StudentResponse updateStudent(Long studentId, StudentUpdateRequest request) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (request.getNameKhmer() != null) student.setNameKhmer(request.getNameKhmer());
        if (request.getNameEnglish() != null) student.setNameEnglish(request.getNameEnglish());
        if (request.getDateOfBirth() != null) student.setDateOfBirth(request.getDateOfBirth());
        if (request.getGender() != null) student.setGender(request.getGender());
        if (request.getLevel() != null) student.setLevel(request.getLevel());
        if (request.getStudentStatus() != null) student.setStudentStatus(request.getStudentStatus());
        if (request.getMedicalNotes() != null) student.setMedicalNotes(request.getMedicalNotes());
        if (request.getPreviousSchool() != null) student.setPreviousSchool(request.getPreviousSchool());

        if (request.getClassId() != null) {
            if (student.getCurrentClass() != null) {
                Class oldClass = student.getCurrentClass();
                oldClass.setCurrentEnrollment(oldClass.getCurrentEnrollment() - 1);
                classRepository.save(oldClass);
            }

            Class newClass = classRepository.findById(request.getClassId())
                    .orElseThrow(() -> new RuntimeException("Class not found"));
            student.setCurrentClass(newClass);
            newClass.setCurrentEnrollment(newClass.getCurrentEnrollment() + 1);
            classRepository.save(newClass);
        }

        // ✅ Now works because Student has removeParent() and addParent() methods
        if (request.getParentIds() != null) {
            // Remove all existing parents
            new ArrayList<>(student.getParents()).forEach(student::removeParent);
            // Add new parents
            List<Parent> parents = parentRepository.findAllById(request.getParentIds());
            for (Parent parent : parents) {
                student.addParent(parent);
            }
        }

        Student updatedStudent = studentRepository.save(student);
        log.info("Student updated: {}", updatedStudent.getNameEnglish());
        return mapToResponse(updatedStudent);
    }

    // ============================================================
    // READ
    // ============================================================

    public StudentResponse getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return mapToResponse(student);
    }

    public StudentResponse getStudentByStudentId(String studentId) {
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
        return mapToResponse(student);
    }

    public StudentResponse getStudentByUserId(Long userId) {
        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found for user ID: " + userId));
        return mapToResponse(student);
    }

    public PageResponse<StudentResponse> getAllStudents(Pageable pageable) {
        Page<Student> page = studentRepository.findAll(pageable);
        return mapToPageResponse(page);
    }

    public List<StudentResponse> getStudentsByClass(Long classId) {
        List<Student> students = studentRepository.findByCurrentClassId(classId);
        return students.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<StudentResponse> getStudentsByParent(Long parentId) {
        List<Student> students = studentRepository.findStudentsByParentId(parentId);
        return students.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<StudentResponse> getStudentsByLevel(String level) {
        try {
            com.sarana_NIS.newTown_backend.model.enums.Level levelEnum =
                    com.sarana_NIS.newTown_backend.model.enums.Level.valueOf(level);
            List<Student> students = studentRepository.findByLevel(levelEnum);
            return students.stream().map(this::mapToResponse).collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid level: " + level);
        }
    }

    public List<StudentResponse> getActiveStudents() {
        List<Student> students = studentRepository.findByStudentStatus(EnrollmentStatus.ACTIVE);
        return students.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // ============================================================
    // BULK TRANSFER
    // ============================================================

    @Transactional
    public StudentClassTransferResponse bulkTransfer(StudentBulkTransferRequest request) {
        Class newClass = classRepository.findById(request.getNewClassId())
                .orElseThrow(() -> new RuntimeException("New class not found"));

        List<Student> students = studentRepository.findAllById(request.getStudentIds());
        List<String> warnings = new ArrayList<>();
        int transferredCount = 0;

        for (Student student : students) {
            if (student.getStudentStatus() != EnrollmentStatus.ACTIVE) {
                warnings.add("Student " + student.getNameEnglish() + " is not active, skipping");
                continue;
            }

            if (student.getCurrentClass() != null) {
                Class oldClass = student.getCurrentClass();
                oldClass.setCurrentEnrollment(oldClass.getCurrentEnrollment() - 1);
                classRepository.save(oldClass);
            }

            student.setCurrentClass(newClass);
            newClass.setCurrentEnrollment(newClass.getCurrentEnrollment() + 1);
            studentRepository.save(student);
            transferredCount++;
        }
        classRepository.save(newClass);

        StudentClassTransferResponse response = new StudentClassTransferResponse();
        response.setSuccess(true);
        response.setMessage(transferredCount + " students transferred successfully");
        if (request.getTransferDate() != null) {
            response.setTransferDate(request.getTransferDate().atStartOfDay());
        } else {
            response.setTransferDate(LocalDate.now().atStartOfDay());
        }
        response.setWarnings(warnings);

        log.info("Bulk transfer: {} students moved to class {}", transferredCount, newClass.getName());
        return response;
    }

    // ============================================================
    // DELETE
    // ============================================================

    @Transactional
    public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (student.getCurrentClass() != null) {
            Class classEntity = student.getCurrentClass();
            classEntity.setCurrentEnrollment(classEntity.getCurrentEnrollment() - 1);
            classRepository.save(classEntity);
        }

        studentRepository.delete(student);
        log.info("Student deleted: {}", student.getNameEnglish());
    }

    @Transactional
    public void softDeleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        student.setStudentStatus(EnrollmentStatus.DROPPED);
        studentRepository.save(student);
        log.info("Student marked as dropped: {}", student.getNameEnglish());
    }

    public boolean existsByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId).isPresent();
    }

    public boolean isStudentActive(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return student.getStudentStatus() == EnrollmentStatus.ACTIVE;
    }

    // ============================================================
    // HELPER METHODS
    // ============================================================

    private StudentResponse mapToResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setStudentId(student.getStudentId());

        if (student.getUser() != null) {
            response.setUser(mapToUserResponse(student.getUser()));
        }

        response.setNameKhmer(student.getNameKhmer());
        response.setNameEnglish(student.getNameEnglish());
        response.setDateOfBirth(student.getDateOfBirth());
        response.setGender(student.getGender());
        response.setLevel(student.getLevel());
        response.setEnrollmentDate(student.getEnrollmentDate());
        response.setStudentStatus(student.getStudentStatus());
        response.setMedicalNotes(student.getMedicalNotes());
        response.setPreviousSchool(student.getPreviousSchool());

        if (student.getCurrentClass() != null) {
            StudentResponse.ClassInfo classInfo = new StudentResponse.ClassInfo();
            classInfo.setId(student.getCurrentClass().getId());
            classInfo.setName(student.getCurrentClass().getName());
            classInfo.setGrade(student.getCurrentClass().getGrade());
            classInfo.setRoom(student.getCurrentClass().getRoom());
            if (student.getCurrentClass().getTeacher() != null) {
                classInfo.setTeacherName(student.getCurrentClass().getTeacher().getFullName());
            }
            response.setCurrentClass(classInfo);
        }

        List<StudentResponse.ParentInfo> parents = student.getParents().stream()
                .map(parent -> {
                    StudentResponse.ParentInfo info = new StudentResponse.ParentInfo();
                    info.setId(parent.getId());
                    info.setFullName(parent.getUser() != null ? parent.getUser().getFullName() : null);
                    info.setPhone(parent.getUser() != null ? parent.getUser().getPhone() : null);
                    info.setEmail(parent.getUser() != null ? parent.getUser().getEmail() : null);
                    info.setRelationship(parent.getRelationship());
                    info.setOccupation(parent.getOccupation());
                    return info;
                })
                .collect(Collectors.toList());
        response.setParents(parents);

        return response;
    }

    private com.sarana_NIS.newTown_backend.dto.response.user.UserResponse mapToUserResponse(User user) {
        if (user == null) return null;
        com.sarana_NIS.newTown_backend.dto.response.user.UserResponse response =
                new com.sarana_NIS.newTown_backend.dto.response.user.UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setUserStatus(user.getUserStatus() != null ? user.getUserStatus().name() : null);
        response.setIsEmailVerified(user.getIsEmailVerified());
        response.setLastLogin(user.getLastLogin());
        response.setAvatarUrl(user.getAvatarUrl());
        return response;
    }

    private PageResponse<StudentResponse> mapToPageResponse(Page<Student> page) {
        List<StudentResponse> content = page.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}