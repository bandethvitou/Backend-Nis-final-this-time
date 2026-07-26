package com.sarana_NIS.newTown_backend.mapper.admissions;

import com.sarana_NIS.newTown_backend.dto.request.academic.CheckinRequest;
import com.sarana_NIS.newTown_backend.dto.response.academic.StudentCheckinResponse;
import com.sarana_NIS.newTown_backend.model.entity.admissions.StudentCheckin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDate;
import java.time.LocalTime;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface StudentCheckinMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "id", source = "id")
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "studentName", source = "student.nameEnglish")
    @Mapping(target = "classId", source = "classEntity.id")
    @Mapping(target = "className", source = "classEntity.name")
    @Mapping(target = "markedByName", source = "markedBy.fullName")
    @Mapping(target = "status", source = "status")
    StudentCheckinResponse toResponse(StudentCheckin checkin);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "classEntity", ignore = true)
    @Mapping(target = "markedBy", ignore = true)
    @Mapping(target = "status", source = "status")
    @Mapping(target = "checkinDate", source = "checkinDate")
    @Mapping(target = "checkinTime", source = "checkinTime")
    @Mapping(target = "checkoutTime", source = "checkoutTime")
    @Mapping(target = "isBulkAction", source = "isBulkAction")
    @Mapping(target = "bulkActionId", source = "bulkActionId")
    @Mapping(target = "lateMinutes", expression = "java(calculateLateMinutes(request))")
    @Mapping(target = "earlyDepartureMinutes", expression = "java(calculateEarlyDepartureMinutes(request))")
    StudentCheckin toEntity(CheckinRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "classEntity", ignore = true)
    @Mapping(target = "markedBy", ignore = true)
    @Mapping(target = "checkinDate", ignore = true)
    @Mapping(target = "isBulkAction", ignore = true)
    @Mapping(target = "bulkActionId", ignore = true)
    void updateEntity(CheckinRequest request, @MappingTarget StudentCheckin checkin);

    // ========== Helper Methods ==========

    default Integer calculateLateMinutes(CheckinRequest request) {
        if (request == null || request.getCheckinTime() == null) return 0;
        LocalTime startTime = LocalTime.of(8, 0); // School starts at 8:00 AM
        if (request.getCheckinTime().isAfter(startTime)) {
            return (int) java.time.Duration.between(startTime, request.getCheckinTime()).toMinutes();
        }
        return 0;
    }

    default Integer calculateEarlyDepartureMinutes(CheckinRequest request) {
        if (request == null || request.getCheckoutTime() == null) return 0;
        LocalTime endTime = LocalTime.of(16, 0); // School ends at 4:00 PM
        if (request.getCheckoutTime().isBefore(endTime)) {
            return (int) java.time.Duration.between(request.getCheckoutTime(), endTime).toMinutes();
        }
        return 0;
    }
}