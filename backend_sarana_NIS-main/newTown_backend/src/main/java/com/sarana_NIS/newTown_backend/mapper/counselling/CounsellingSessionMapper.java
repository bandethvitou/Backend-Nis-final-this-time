package com.sarana_NIS.newTown_backend.mapper.counselling;

import com.sarana_NIS.newTown_backend.dto.request.counselling.CounsellingSessionRequest;
import com.sarana_NIS.newTown_backend.dto.request.counselling.SessionNoteRequest;
import com.sarana_NIS.newTown_backend.dto.response.counselling.CounsellingSessionResponse;
import com.sarana_NIS.newTown_backend.model.entity.counselling.CounsellingSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CounsellingSessionMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "id", source = "id")
    @Mapping(target = "studentId", source = "counsellingCase.student.id")
    @Mapping(target = "studentName", source = "counsellingCase.student.nameEnglish")
    @Mapping(target = "caseId", source = "counsellingCase.id")
    @Mapping(target = "counsellorId", source = "counsellor.id")
    @Mapping(target = "counsellorName", source = "counsellor.fullName")
    CounsellingSessionResponse toResponse(CounsellingSession session);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "counsellingCase", ignore = true)
    @Mapping(target = "counsellor", ignore = true)
    @Mapping(target = "followUpRequired", constant = "false")
    @Mapping(target = "followUpDate", ignore = true)
    @Mapping(target = "durationMinutes", source = "duration")  // ✅ Map duration to durationMinutes
    CounsellingSession toEntity(CounsellingSessionRequest request);

    // ========== Session Note Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "counsellingCase", ignore = true)
    @Mapping(target = "counsellor", ignore = true)
    @Mapping(target = "notes", source = "notes")
    @Mapping(target = "followUpRequired", source = "followUpRequired")
    @Mapping(target = "followUpDate", source = "followUpDate")
    @Mapping(target = "durationMinutes", source = "duration")  // ✅ Map duration to durationMinutes
    CounsellingSession toEntity(SessionNoteRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "counsellingCase", ignore = true)
    @Mapping(target = "counsellor", ignore = true)
    @Mapping(target = "durationMinutes", source = "duration")  // ✅ Map duration to durationMinutes
    void updateEntity(CounsellingSessionRequest request, @MappingTarget CounsellingSession session);

    // ========== Session Note Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "counsellingCase", ignore = true)
    @Mapping(target = "counsellor", ignore = true)
    @Mapping(target = "sessionDate", ignore = true)
    @Mapping(target = "sessionType", ignore = true)
    @Mapping(target = "durationMinutes", ignore = true)  // ✅ Use durationMinutes
    void updateFromNote(SessionNoteRequest request, @MappingTarget CounsellingSession session);
}