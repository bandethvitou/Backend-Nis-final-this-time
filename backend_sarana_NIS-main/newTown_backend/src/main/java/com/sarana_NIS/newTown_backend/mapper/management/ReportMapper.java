package com.sarana_NIS.newTown_backend.mapper.management;

import com.sarana_NIS.newTown_backend.dto.request.management.ReportRequest;
import com.sarana_NIS.newTown_backend.dto.response.management.ReportResponse;
import com.sarana_NIS.newTown_backend.dto.response.management.ReportStatsResponse;
import com.sarana_NIS.newTown_backend.model.entity.management.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ReportMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "id", source = "id")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "submittedBy", source = "submittedBy.fullName")
    @Mapping(target = "submittedByFullName", source = "submittedBy.fullName")
    @Mapping(target = "submittedTo", source = "submittedBy.fullName")
    @Mapping(target = "submittedToFullName", source = "submittedBy.fullName")
    @Mapping(target = "reviewedBy", source = "reviewedBy.fullName")
    @Mapping(target = "reviewedByName", source = "reviewedBy.fullName")
    @Mapping(target = "submittedDate", source = "submittedDate")
    @Mapping(target = "reviewedDate", source = "reviewedDate")
    @Mapping(target = "feedback", source = "feedback")
    @Mapping(target = "revisionRequired", source = "revisionRequired")
    @Mapping(target = "isConfidential", source = "isConfidential")
    @Mapping(target = "viewCount", source = "viewCount")
    // ✅ FIXED: attachments and tags are DTO-only fields - ignore them
    @Mapping(target = "attachments", ignore = true)
    @Mapping(target = "tags", ignore = true)
    // Ignore other DTO-only fields
    @Mapping(target = "approvedBy", ignore = true)
    @Mapping(target = "approvedByFullName", ignore = true)
    @Mapping(target = "approvedDate", ignore = true)
    @Mapping(target = "lastViewedAt", ignore = true)
    @Mapping(target = "priority", ignore = true)
    @Mapping(target = "reportPeriodStart", ignore = true)
    @Mapping(target = "reportPeriodEnd", ignore = true)
    @Mapping(target = "data", ignore = true)
    @Mapping(target = "revisionComment", ignore = true)
    @Mapping(target = "metrics", ignore = true)
    @Mapping(target = "executiveSummary", ignore = true)
    @Mapping(target = "recommendations", ignore = true)
    @Mapping(target = "nextSteps", ignore = true)
    ReportResponse toResponse(Report report);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "submittedBy", ignore = true)
    @Mapping(target = "submittedTo", ignore = true)
    @Mapping(target = "reviewedBy", ignore = true)
    @Mapping(target = "type", source = "type")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "status", constant = "DRAFT")
    @Mapping(target = "submittedDate", ignore = true)
    @Mapping(target = "reviewedDate", ignore = true)
    @Mapping(target = "revisionRequired", constant = "false")
    @Mapping(target = "viewCount", constant = "0")
    @Mapping(target = "isConfidential", constant = "false")
    @Mapping(target = "feedback", ignore = true)
    // ✅ FIXED: These fields don't exist in Report entity - REMOVED completely
    // @Mapping(target = "attachments", ignore = true)
    // @Mapping(target = "tags", ignore = true)
    Report toEntity(ReportRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "submittedBy", ignore = true)
    @Mapping(target = "submittedTo", ignore = true)
    @Mapping(target = "reviewedBy", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "type", source = "type")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "submittedDate", ignore = true)
    @Mapping(target = "reviewedDate", ignore = true)
    @Mapping(target = "revisionRequired", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "isConfidential", ignore = true)
    @Mapping(target = "feedback", ignore = true)
    // ✅ FIXED: These fields don't exist in Report entity - REMOVED completely
    // @Mapping(target = "attachments", ignore = true)
    // @Mapping(target = "tags", ignore = true)
    void updateEntity(ReportRequest request, @MappingTarget Report report);

    // ========== Stats Mapping ==========
    default ReportStatsResponse toStatsResponse(List<Report> reports) {
        if (reports == null) return new ReportStatsResponse();

        ReportStatsResponse stats = new ReportStatsResponse();
        stats.setTotalReports(reports.size());

        int draft = 0, submitted = 0, underReview = 0, approved = 0, rejected = 0, revisionRequests = 0;

        for (Report r : reports) {
            if (r.getStatus() != null) {
                switch (r.getStatus()) {
                    case DRAFT: draft++; break;
                    case SUBMITTED: submitted++; break;
                    case UNDER_REVIEW: underReview++; break;
                    case APPROVED: approved++; break;
                    case REJECTED: rejected++; break;
                    default: break;
                }
            }
            if (r.getRevisionRequired() != null && r.getRevisionRequired()) {
                revisionRequests++;
            }
        }

        stats.setDraft(draft);
        stats.setSubmitted(submitted);
        stats.setUnderReview(underReview);
        stats.setApproved(approved);
        stats.setRejected(rejected);
        stats.setRevisionRequests(revisionRequests);
        stats.setApprovalRate(reports.size() > 0 ? (double) approved / reports.size() * 100 : 0);

        return stats;
    }
}