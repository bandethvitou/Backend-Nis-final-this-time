package com.sarana_NIS.newTown_backend.mapper.user;

import com.sarana_NIS.newTown_backend.dto.request.user.StudentCreateRequest;
import com.sarana_NIS.newTown_backend.dto.request.user.StudentUpdateRequest;
import com.sarana_NIS.newTown_backend.dto.response.user.StudentResponse;
import com.sarana_NIS.newTown_backend.dto.response.user.StudentResponse.ClassInfo;
import com.sarana_NIS.newTown_backend.dto.response.user.StudentResponse.ParentInfo;
import com.sarana_NIS.newTown_backend.model.entity.user.Parent;
import com.sarana_NIS.newTown_backend.model.entity.user.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface StudentMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "user", expression = "java(mapUser(student.getUser()))")
    @Mapping(target = "currentClass", expression = "java(mapClass(student.getCurrentClass()))")
    @Mapping(target = "parents", expression = "java(mapParents(student.getParents()))")
    @Mapping(target = "currentGPA", expression = "java(calculateGPA(student))")
    @Mapping(target = "attendanceRate", expression = "java(calculateAttendanceRate(student))")
    @Mapping(target = "isAtRisk", expression = "java(checkAtRisk(student))")
    @Mapping(target = "riskLevel", expression = "java(determineRiskLevel(student))")
    StudentResponse toResponse(Student student);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "currentClass", ignore = true)
    @Mapping(target = "parents", ignore = true)
    @Mapping(target = "attendances", ignore = true)
    @Mapping(target = "scores", ignore = true)
    @Mapping(target = "studentStatus", constant = "ACTIVE")
    @Mapping(target = "enrollmentDate", expression = "java(java.time.LocalDate.now())")
    Student toEntity(StudentCreateRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "studentId", ignore = true)
    @Mapping(target = "attendances", ignore = true)
    @Mapping(target = "scores", ignore = true)
    @Mapping(target = "parents", ignore = true)
    void updateEntity(StudentUpdateRequest request, @MappingTarget Student student);

    // ========== Helper Methods ==========

    // Map User
    default com.sarana_NIS.newTown_backend.dto.response.user.UserResponse mapUser(com.sarana_NIS.newTown_backend.model.entity.core.User user) {
        if (user == null) return null;
        com.sarana_NIS.newTown_backend.dto.response.user.UserResponse response =
                new com.sarana_NIS.newTown_backend.dto.response.user.UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setPhone(user.getPhone());
        response.setAvatarUrl(user.getAvatarUrl());
        response.setUserStatus(user.getUserStatus() != null ? user.getUserStatus().name() : null);
        response.setIsEmailVerified(user.getIsEmailVerified());
        response.setLastLogin(user.getLastLogin());
        return response;
    }

    // Map Class
    default ClassInfo mapClass(com.sarana_NIS.newTown_backend.model.entity.academic.Class classEntity) {
        if (classEntity == null) return null;
        ClassInfo info = new ClassInfo();
        info.setId(classEntity.getId());
        info.setName(classEntity.getName());
        info.setGrade(classEntity.getGrade());
        info.setRoom(classEntity.getRoom());
        info.setTeacherName(classEntity.getTeacher() != null ? classEntity.getTeacher().getFullName() : null);
        return info;
    }

    // Map Parents
    default List<ParentInfo> mapParents(Set<Parent> parents) {
        if (parents == null) return new java.util.ArrayList<>();
        return parents.stream().map(p -> {
            ParentInfo info = new ParentInfo();
            info.setId(p.getId());
            info.setFullName(p.getUser() != null ? p.getUser().getFullName() : null);
            info.setPhone(p.getUser() != null ? p.getUser().getPhone() : null);
            info.setEmail(p.getUser() != null ? p.getUser().getEmail() : null);
            info.setRelationship(p.getRelationship());
            info.setOccupation(p.getOccupation());
            return info;
        }).collect(Collectors.toList());
    }

    // Calculate GPA
    default Double calculateGPA(Student student) {
        if (student == null || student.getScores() == null || student.getScores().isEmpty()) {
            return null;
        }
        return student.getScores().stream()
                .filter(s -> s.getTotalScore() != null)
                .mapToDouble(s -> s.getTotalScore())
                .average()
                .orElse(0.0);
    }

    // Calculate Attendance Rate
    default Integer calculateAttendanceRate(Student student) {
        if (student == null || student.getAttendances() == null || student.getAttendances().isEmpty()) {
            return null;
        }
        long total = student.getAttendances().size();
        long present = student.getAttendances().stream()
                .filter(a -> a.getStatus() != null && a.getStatus().name().equals("PRESENT"))
                .count();
        return total > 0 ? (int) ((present * 100) / total) : 0;
    }

    // Check At Risk
    default Boolean checkAtRisk(Student student) {
        if (student == null) return false;
        Double gpa = calculateGPA(student);
        Integer attendance = calculateAttendanceRate(student);
        return (gpa != null && gpa < 50) || (attendance != null && attendance < 75);
    }

    // Determine Risk Level
    default String determineRiskLevel(Student student) {
        if (student == null) return null;
        Double gpa = calculateGPA(student);
        Integer attendance = calculateAttendanceRate(student);

        if ((gpa != null && gpa < 40) || (attendance != null && attendance < 60)) {
            return "HIGH";
        } else if ((gpa != null && gpa < 50) || (attendance != null && attendance < 75)) {
            return "MEDIUM";
        } else if ((gpa != null && gpa < 60) || (attendance != null && attendance < 85)) {
            return "LOW";
        }
        return "NONE";
    }
}