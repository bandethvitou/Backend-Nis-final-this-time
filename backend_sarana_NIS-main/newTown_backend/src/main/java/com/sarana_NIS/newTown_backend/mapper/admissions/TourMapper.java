package com.sarana_NIS.newTown_backend.mapper.admissions;

import com.sarana_NIS.newTown_backend.dto.request.admissions.TourRequest;
import com.sarana_NIS.newTown_backend.dto.response.admissions.AdmissionsDashboardResponse;
import com.sarana_NIS.newTown_backend.dto.response.admissions.TourResponse;
import com.sarana_NIS.newTown_backend.model.entity.admissions.Tour;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TourMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "id", source = "id")  // ✅ ADD THIS - Explicitly map id
    @Mapping(target = "createdBy", expression = "java(getCreatedBy(tour))")
    TourResponse toResponse(Tour tour);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "status", constant = "SCHEDULED")
    Tour toEntity(TourRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntity(TourRequest request, @MappingTarget Tour tour);

    // ========== Dashboard Summary ==========
    default AdmissionsDashboardResponse.TourSummary toTourSummary(Tour tour) {
        if (tour == null) return null;

        AdmissionsDashboardResponse.TourSummary summary =
                new AdmissionsDashboardResponse.TourSummary();

        // ✅ Now works because Tour extends BaseEntity which has getId()
        summary.setId(tour.getId());
        summary.setParentName(tour.getParentName());
        summary.setStudentName(tour.getStudentName());
        summary.setTourDate(tour.getTourDate());
        summary.setStatus(tour.getStatus() != null ?
                tour.getStatus().name() : null);

        if (tour.getTourTime() != null) {
            summary.setTime(tour.getTourTime()
                    .format(DateTimeFormatter.ofPattern("hh:mm a")));
        }

        return summary;
    }

    // ========== Batch Mapping ==========
    default List<AdmissionsDashboardResponse.TourSummary> toTourSummaries(
            List<Tour> tours) {
        if (tours == null) return new java.util.ArrayList<>();
        return tours.stream()
                .map(this::toTourSummary)
                .collect(Collectors.toList());
    }

    // ========== Helper Methods ==========

    default String getCreatedBy(Tour tour) {
        // If Tour has createdBy from BaseEntity
        if (tour != null && tour.getCreatedBy() != null) {
            return String.valueOf(tour.getCreatedBy());
        }
        return "System";
    }
}