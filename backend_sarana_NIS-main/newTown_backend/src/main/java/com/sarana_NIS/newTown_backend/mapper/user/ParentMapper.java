package com.sarana_NIS.newTown_backend.mapper.user;

import com.sarana_NIS.newTown_backend.dto.request.user.ParentCreateRequest;
import com.sarana_NIS.newTown_backend.dto.response.user.ParentResponse;
import com.sarana_NIS.newTown_backend.dto.response.user.ParentResponse.StudentInfo;
import com.sarana_NIS.newTown_backend.dto.response.user.UserResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.user.Parent;
import com.sarana_NIS.newTown_backend.model.entity.user.Student;
import com.sarana_NIS.newTown_backend.model.enums.UserStatus;
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
public interface ParentMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "user", expression = "java(mapUser(parent.getUser()))")
    @Mapping(target = "students", expression = "java(mapStudents(parent.getStudents()))")
    ParentResponse toResponse(Parent parent);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "students", ignore = true)
    Parent toEntity(ParentCreateRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "students", ignore = true)
    void updateEntity(ParentCreateRequest request, @MappingTarget Parent parent);

    // ========== Helper Methods ==========

    // Map User
    default UserResponse mapUser(User user) {
        if (user == null) return null;
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setPhone(user.getPhone());
        response.setAvatarUrl(user.getAvatarUrl());

        UserStatus userStatus = user.getUserStatus();
        response.setUserStatus(userStatus != null ? userStatus.name() : null);

        response.setIsEmailVerified(user.getIsEmailVerified());
        response.setLastLogin(user.getLastLogin());
        return response;
    }

    // Map Students
    default List<StudentInfo> mapStudents(Set<Student> students) {
        if (students == null) return new java.util.ArrayList<>();
        return students.stream().map(s -> {
            StudentInfo info = new StudentInfo();
            info.setId(s.getId());
            info.setStudentId(s.getStudentId());
            info.setFullName(s.getNameEnglish());
            info.setLevel(s.getLevel() != null ? s.getLevel().name() : null);
            info.setClassName(s.getCurrentClass() != null ? s.getCurrentClass().getName() : null);
            info.setStatus(s.getStudentStatus() != null ? s.getStudentStatus().name() : null);
            // ✅ Fix: Use null instead of calling calculateGPA() if method doesn't exist
            info.setGpa(null);
            return info;
        }).collect(Collectors.toList());
    }
}