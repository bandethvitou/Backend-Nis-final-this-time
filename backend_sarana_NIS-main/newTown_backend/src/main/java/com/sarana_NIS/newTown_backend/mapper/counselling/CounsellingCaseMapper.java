package com.sarana_NIS.newTown_backend.mapper.counselling;

import com.sarana_NIS.newTown_backend.dto.request.counselling.CounsellingCaseRequest;
import com.sarana_NIS.newTown_backend.dto.response.counselling.CaseSummaryResponse;
import com.sarana_NIS.newTown_backend.dto.response.counselling.CounsellingCaseResponse;
import com.sarana_NIS.newTown_backend.model.entity.counselling.CounsellingCase;
import com.sarana_NIS.newTown_backend.model.enums.CaseStatus;
import com.sarana_NIS.newTown_backend.model.enums.IssueSeverity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CounsellingCaseMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "studentName", source = "student.nameEnglish")
    @Mapping(target = "openedBy", source = "openedBy.fullName")
    @Mapping(target = "sessionCount", ignore = true)
    @Mapping(target = "lastSessionAt", ignore = true)
    @Mapping(target = "isAtRisk", expression = "java(counsellingCase.getSeverity() == com.sarana_NIS.newTown_backend.model.enums.IssueSeverity.HIGH || counsellingCase.getSeverity() == com.sarana_NIS.newTown_backend.model.enums.IssueSeverity.CRITICAL)")
    @Mapping(target = "actionPlan", ignore = true)
    CounsellingCaseResponse toResponse(CounsellingCase counsellingCase);

    // ========== Entity → Summary Response ==========
    @Mapping(target = "studentName", source = "student.nameEnglish")
    @Mapping(target = "studentId", source = "student.studentId")
    @Mapping(target = "openedBy", source = "openedBy.fullName")
    @Mapping(target = "sessionCount", ignore = true)
    @Mapping(target = "lastSessionAt", ignore = true)
    @Mapping(target = "isAtRisk", expression = "java(counsellingCase.getSeverity() == com.sarana_NIS.newTown_backend.model.enums.IssueSeverity.HIGH || counsellingCase.getSeverity() == com.sarana_NIS.newTown_backend.model.enums.IssueSeverity.CRITICAL)")
    CaseSummaryResponse toSummaryResponse(CounsellingCase counsellingCase);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "openedBy", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "openedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "closedAt", ignore = true)
    CounsellingCase toEntity(CounsellingCaseRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "openedBy", ignore = true)
    @Mapping(target = "openedAt", ignore = true)
    @Mapping(target = "closedAt", ignore = true)
    void updateEntity(CounsellingCaseRequest request, @MappingTarget CounsellingCase counsellingCase);

    // ========== List Mappings ==========
    List<CounsellingCaseResponse> toResponseList(List<CounsellingCase> counsellingCases);

    List<CaseSummaryResponse> toSummaryResponseList(List<CounsellingCase> counsellingCases);

    List<CounsellingCase> toEntityList(List<CounsellingCaseRequest> requests);
}