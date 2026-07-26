package com.sarana_NIS.newTown_backend.mapper.communication;

import com.sarana_NIS.newTown_backend.dto.request.communication.CorrespondenceRequest;
import com.sarana_NIS.newTown_backend.dto.response.communication.CorrespondenceResponse;
import com.sarana_NIS.newTown_backend.model.entity.communication.Correspondence;
import com.sarana_NIS.newTown_backend.model.enums.Priority;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CorrespondenceMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "id", source = "id")
    @Mapping(target = "createdById", expression = "java(getCreatedById(correspondence))")
    @Mapping(target = "createdByName", expression = "java(getCreatedByName(correspondence))")
    @Mapping(target = "createdBy", expression = "java(getCreatedByName(correspondence))")
    @Mapping(target = "daysSince", expression = "java(calculateDaysSince(correspondence.getDate()))")
    @Mapping(target = "isUrgent", expression = "java(isUrgent(correspondence))")
    CorrespondenceResponse toResponse(Correspondence correspondence);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)  // ✅ createdBy is Long in BaseEntity
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "date", expression = "java(java.time.LocalDate.now())")
    Correspondence toEntity(CorrespondenceRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "date", ignore = true)
    void updateEntity(CorrespondenceRequest request, @MappingTarget Correspondence correspondence);

    // ========== Helper Methods ==========

    default Long getCreatedById(Correspondence correspondence) {
        if (correspondence == null) {
            return null;
        }
        return correspondence.getCreatedBy();
    }

    default String getCreatedByName(Correspondence correspondence) {
        if (correspondence == null || correspondence.getCreatedBy() == null) {
            return null;
        }
        // Since createdBy is just a Long ID, we don't have the name
        // You would need to fetch the user by ID from a service
        return "User " + correspondence.getCreatedBy();
    }

    default Integer calculateDaysSince(LocalDate date) {
        if (date == null) return 0;
        return (int) ChronoUnit.DAYS.between(date, LocalDate.now());
    }

    default Boolean isUrgent(Correspondence correspondence) {
        if (correspondence == null) return false;
        if (correspondence.getPriority() == Priority.HIGH) return true;
        if (correspondence.getStatus() != null &&
                correspondence.getStatus().name().equals("PENDING")) {
            Integer days = calculateDaysSince(correspondence.getDate());
            return days != null && days > 7;
        }
        return false;
    }
}