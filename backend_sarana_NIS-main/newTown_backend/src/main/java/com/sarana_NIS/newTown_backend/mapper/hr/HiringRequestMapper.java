package com.sarana_NIS.newTown_backend.mapper.hr;

import com.sarana_NIS.newTown_backend.dto.request.hr.HiringRequestDTO;
import com.sarana_NIS.newTown_backend.dto.response.hr.HiringResponse;
import com.sarana_NIS.newTown_backend.dto.response.hr.HiringSummaryResponse;
import com.sarana_NIS.newTown_backend.model.entity.hr.HiringRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface HiringRequestMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "id", source = "id")  // ✅ Explicitly map id
    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "requestedBy", source = "requestedBy.fullName")
    @Mapping(target = "requestedByName", source = "requestedBy.fullName")
    @Mapping(target = "approvedBy", source = "approvedBy.fullName")
    @Mapping(target = "approvedByName", source = "approvedBy.fullName")
    HiringResponse toResponse(HiringRequest hiringRequest);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "requestedBy", ignore = true)
    @Mapping(target = "approvedBy", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    HiringRequest toEntity(HiringRequestDTO request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "requestedBy", ignore = true)
    @Mapping(target = "approvedBy", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntity(HiringRequestDTO request, @MappingTarget HiringRequest hiringRequest);

    // ========== Summary Mapping ==========
    default HiringSummaryResponse toSummaryResponse(List<HiringRequest> requests) {
        if (requests == null) return new HiringSummaryResponse();

        HiringSummaryResponse summary = new HiringSummaryResponse();
        summary.setTotalRequests(requests.size());

        long pending = requests.stream()
                .filter(r -> r.getStatus() != null && r.getStatus().name().equals("PENDING"))
                .count();
        long approved = requests.stream()
                .filter(r -> r.getStatus() != null && r.getStatus().name().equals("APPROVED"))
                .count();
        long rejected = requests.stream()
                .filter(r -> r.getStatus() != null && r.getStatus().name().equals("REJECTED"))
                .count();

        summary.setPendingRequests((int) pending);
        summary.setApprovedRequests((int) approved);
        summary.setRejectedRequests((int) rejected);

        // Position summary
        requests.stream()
                .collect(java.util.stream.Collectors.groupingBy(HiringRequest::getPosition))
                .forEach((position, list) -> {
                    HiringSummaryResponse.PositionHiringSummary posSummary =
                            new HiringSummaryResponse.PositionHiringSummary();
                    posSummary.setPosition(position);
                    posSummary.setRequested(list.size());
                    posSummary.setApproved((int) list.stream()
                            .filter(r -> r.getStatus() != null && r.getStatus().name().equals("APPROVED"))
                            .count());
                    summary.getByPosition().add(posSummary);
                });

        // Department summary
        requests.stream()
                .filter(r -> r.getDepartment() != null)
                .collect(java.util.stream.Collectors.groupingBy(r -> r.getDepartment().getName()))
                .forEach((dept, list) -> {
                    HiringSummaryResponse.DepartmentHiringSummary deptSummary =
                            new HiringSummaryResponse.DepartmentHiringSummary();
                    deptSummary.setDepartment(dept);
                    deptSummary.setRequests(list.size());
                    deptSummary.setApproved((int) list.stream()
                            .filter(r -> r.getStatus() != null && r.getStatus().name().equals("APPROVED"))
                            .count());
                    summary.getByDepartment().add(deptSummary);
                });

        return summary;
    }
}