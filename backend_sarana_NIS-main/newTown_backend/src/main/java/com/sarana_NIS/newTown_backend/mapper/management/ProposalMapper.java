package com.sarana_NIS.newTown_backend.mapper.management;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarana_NIS.newTown_backend.dto.request.management.ProposalRequest;
import com.sarana_NIS.newTown_backend.dto.response.management.ProposalResponse;
import com.sarana_NIS.newTown_backend.dto.response.management.ProposalStatsResponse;
import com.sarana_NIS.newTown_backend.model.entity.management.Proposal;
import com.sarana_NIS.newTown_backend.model.enums.ProposalStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProposalMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "submittedById", source = "submittedBy.id")
    @Mapping(target = "submittedByName", source = "submittedBy.fullName")
    @Mapping(target = "submittedBy", source = "submittedBy.fullName")
    @Mapping(target = "approvedById", source = "approvedBy.id")
    @Mapping(target = "approvedByName", source = "approvedBy.fullName")
    @Mapping(target = "approvedBy", source = "approvedBy.fullName")
    @Mapping(target = "reviewedById", source = "reviewedBy.id")
    @Mapping(target = "reviewedByName", source = "reviewedBy.fullName")
    @Mapping(target = "reviewedBy", source = "reviewedBy.fullName")
    @Mapping(target = "attachments", expression = "java(parseAttachments(proposal.getAttachments()))")
    ProposalResponse toResponse(Proposal proposal);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "submittedBy", ignore = true)
    @Mapping(target = "approvedBy", ignore = true)
    @Mapping(target = "reviewedBy", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "submittedDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "reviewedDate", ignore = true)
    @Mapping(target = "approvedDate", ignore = true)
    @Mapping(target = "rejectionReason", ignore = true)
    @Mapping(target = "isEscalated", constant = "false")
    @Mapping(target = "escalatedTo", ignore = true)
    @Mapping(target = "escalatedAt", ignore = true)
    Proposal toEntity(ProposalRequest request);

    // ========== Stats Mapping ==========
    default ProposalStatsResponse toStatsResponse(List<Proposal> proposals) {
        if (proposals == null) return new ProposalStatsResponse();

        ProposalStatsResponse stats = new ProposalStatsResponse();
        stats.setTotalProposals(proposals.size());

        int pending = 0, approved = 0, rejected = 0, escalated = 0;
        int underReview = 0, implemented = 0, cancelled = 0;

        for (Proposal p : proposals) {
            if (p.getStatus() != null) {
                switch (p.getStatus()) {
                    case PENDING:
                        pending++;
                        break;
                    case UNDER_REVIEW:
                        underReview++;
                        break;
                    case APPROVED:
                        approved++;
                        break;
                    case REJECTED:
                        rejected++;
                        break;
                    case ESCALATED:
                        escalated++;
                        break;
                    case IMPLEMENTED:
                        implemented++;
                        break;
                    case CANCELLED:
                        cancelled++;
                        break;
                    default:
                        break;
                }
            }
            // Check isEscalated - using proper getter
            if (p.getIsEscalated() != null && p.getIsEscalated()) {
                escalated++;
            }
        }

        // Set values using int parameters (will be converted to Long via overloaded methods)
        stats.setPending(pending);
        stats.setUnderReview(underReview);
        stats.setApproved(approved);
        stats.setRejected(rejected);
        stats.setEscalated(escalated);
        stats.setImplemented(implemented);
        stats.setCancelled(cancelled);
        stats.setEscalatedCount(escalated);  // Now this method exists

        // Calculate approval rate
        if (proposals.size() > 0) {
            stats.setApprovalRate((double) approved / proposals.size() * 100);
        } else {
            stats.setApprovalRate(0.0);
        }

        // By type stats - group by type
        if (proposals.stream().anyMatch(p -> p.getType() != null)) {
            proposals.stream()
                    .collect(Collectors.groupingBy(p -> p.getType() != null ? p.getType() : "UNKNOWN"))
                    .forEach((type, list) -> {
                        ProposalStatsResponse.ProposalTypeStats typeStats =
                                new ProposalStatsResponse.ProposalTypeStats();
                        typeStats.setType(type);
                        typeStats.setCount(list.size());

                        // Calculate total budget
                        double totalBudget = list.stream()
                                .filter(p -> p.getBudgetAmount() != null)
                                .mapToDouble(Proposal::getBudgetAmount)
                                .sum();
                        typeStats.setTotalBudget(totalBudget);

                        // Count approved
                        long approvedCount = list.stream()
                                .filter(p -> p.getStatus() == ProposalStatus.APPROVED)
                                .count();
                        typeStats.setApproved((int) approvedCount);

                        // Calculate approval rate
                        if (list.size() > 0) {
                            typeStats.setApprovalRate((double) approvedCount / list.size() * 100);
                        } else {
                            typeStats.setApprovalRate(0.0);
                        }

                        stats.getByType().add(typeStats);
                    });
        }

        return stats;
    }

    // ========== Helper Methods ==========

    default List<String> parseAttachments(String attachmentsJson) {
        if (attachmentsJson == null || attachmentsJson.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(attachmentsJson, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}