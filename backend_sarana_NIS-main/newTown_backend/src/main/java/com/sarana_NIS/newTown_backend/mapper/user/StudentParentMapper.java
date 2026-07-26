package com.sarana_NIS.newTown_backend.mapper.user;

import com.sarana_NIS.newTown_backend.dto.request.user.StudentParentRequest;
import com.sarana_NIS.newTown_backend.dto.response.user.StudentParentResponse;
import com.sarana_NIS.newTown_backend.model.entity.user.StudentParent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface StudentParentMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "studentName", source = "student.nameEnglish")
    @Mapping(target = "studentIdNumber", source = "student.studentId")
    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "parentName", source = "parent.user.fullName")
    @Mapping(target = "parentPhone", source = "parent.user.phone")
    @Mapping(target = "parentEmail", source = "parent.user.email")
    @Mapping(target = "relationshipDisplay", expression = "java(getRelationshipDisplay(studentParent.getRelationship()))")
    StudentParentResponse toResponse(StudentParent studentParent);

    // ========== Request → Entity ==========
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "relationship", source = "relationship")
    StudentParent toEntity(StudentParentRequest request);

    // ========== Helper Methods ==========

    default String getRelationshipDisplay(com.sarana_NIS.newTown_backend.model.enums.Relationship relationship) {
        if (relationship == null) return null;
        switch (relationship) {
            case FATHER: return "Father";
            case MOTHER: return "Mother";
            case GUARDIAN: return "Guardian";
            case OTHER: return "Other";
            default: return relationship.name();
        }
    }
}