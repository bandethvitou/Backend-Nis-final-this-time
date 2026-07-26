package com.sarana_NIS.newTown_backend.mapper.admissions;

import com.sarana_NIS.newTown_backend.dto.request.admissions.ApplicationRequest;
import com.sarana_NIS.newTown_backend.dto.response.admissions.ApplicationResponse;
import com.sarana_NIS.newTown_backend.dto.response.admissions.ApplicationStatsResponse;
import com.sarana_NIS.newTown_backend.dto.response.admissions.ApplicationTimelineResponse;
import com.sarana_NIS.newTown_backend.model.entity.admissions.Application;
import com.sarana_NIS.newTown_backend.model.enums.ApplicationStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ApplicationMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "id", source = "id")
    @Mapping(target = "targetClassId", source = "targetClass.id")
    @Mapping(target = "targetClassName", source = "targetClass.name")
    @Mapping(target = "reviewedById", source = "reviewedBy.id")
    @Mapping(target = "reviewedByName", source = "reviewedBy.fullName")
    ApplicationResponse toResponse(Application application);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "targetClass", ignore = true)
    @Mapping(target = "reviewedBy", ignore = true)
    @Mapping(target = "status", constant = "DRAFT")
    @Mapping(target = "inquiryDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "tourDate", ignore = true)
    @Mapping(target = "submittedDate", ignore = true)
    @Mapping(target = "reviewedDate", ignore = true)
    @Mapping(target = "approvedDate", ignore = true)
    @Mapping(target = "invoiceSentDate", ignore = true)
    @Mapping(target = "paymentReceivedDate", ignore = true)
    @Mapping(target = "enrolledDate", ignore = true)
    @Mapping(target = "reviewNotes", ignore = true)
    @Mapping(target = "rejectionReason", ignore = true)
    @Mapping(target = "documentsRequested", ignore = true)
    Application toEntity(ApplicationRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "targetClass", ignore = true)
    @Mapping(target = "reviewedBy", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "inquiryDate", ignore = true)
    @Mapping(target = "tourDate", ignore = true)
    @Mapping(target = "submittedDate", ignore = true)
    @Mapping(target = "reviewedDate", ignore = true)
    @Mapping(target = "approvedDate", ignore = true)
    @Mapping(target = "invoiceSentDate", ignore = true)
    @Mapping(target = "paymentReceivedDate", ignore = true)
    @Mapping(target = "enrolledDate", ignore = true)
    @Mapping(target = "reviewNotes", ignore = true)
    @Mapping(target = "rejectionReason", ignore = true)
    @Mapping(target = "documentsRequested", ignore = true)
    void updateEntity(ApplicationRequest request, @MappingTarget Application application);

    // ========== Stats Response ==========
    default ApplicationStatsResponse toStatsResponse(List<Application> applications) {
        if (applications == null) return new ApplicationStatsResponse();

        ApplicationStatsResponse stats = new ApplicationStatsResponse();
        stats.setTotalApplications(applications.size());

        int pending = 0, approved = 0, rejected = 0, enrolled = 0;

        for (Application app : applications) {
            if (app.getStatus() != null) {
                switch (app.getStatus()) {
                    case DRAFT:
                    case SUBMITTED:
                    case UNDER_REVIEW:
                    case DOCUMENTS_REQUESTED:
                        pending++;
                        break;
                    case APPROVED:
                    case INVOICE_SENT:
                    case PAID:
                        approved++;
                        break;
                    case REJECTED:
                        rejected++;
                        break;
                    case ENROLLED:
                        enrolled++;
                        break;
                }
            }

            // Status breakdown
            if (app.getStatus() != null) {
                stats.getStatusBreakdown().merge(app.getStatus().name(), 1, Integer::sum);
            }

            // Level breakdown
            if (app.getLevel() != null) {
                stats.getLevelBreakdown().merge(app.getLevel().name(), 1, Integer::sum);
            }
        }

        stats.setPendingApplications(pending);
        stats.setApprovedApplications(approved);
        stats.setRejectedApplications(rejected);
        stats.setEnrolledApplications(enrolled);

        // Calculate rates
        if (applications.size() > 0) {
            stats.setConversionRate((double) enrolled / applications.size() * 100);
            stats.setApprovalRate((double) approved / applications.size() * 100);
        } else {
            stats.setConversionRate(0.0);
            stats.setApprovalRate(0.0);
        }

        return stats;
    }

    // ========== Timeline Response ==========
    default ApplicationTimelineResponse toTimelineResponse(Application application) {
        if (application == null) return null;

        ApplicationTimelineResponse response = new ApplicationTimelineResponse();
        response.setApplicationId(application.getId());
        response.setStudentName(application.getStudentName());
        response.setParentName(application.getParentName());
        response.setParentPhone(application.getParentPhone());
        response.setStatus(application.getStatus() != null ?
                application.getStatus().name() : null);
        response.setIsCompleted(application.getStatus() == ApplicationStatus.ENROLLED ||
                application.getStatus() == ApplicationStatus.REJECTED);

        // Build timeline events
        addTimelineEvent(response, "Inquiry", "Application initiated",
                application.getInquiryDate());

        if (application.getTourDate() != null) {
            addTimelineEvent(response, "Tour", "Campus tour scheduled",
                    application.getTourDate());
        }

        if (application.getSubmittedDate() != null) {
            addTimelineEvent(response, "Submitted", "Application submitted",
                    application.getSubmittedDate());
        }

        if (application.getReviewedDate() != null) {
            addTimelineEvent(response, "Review", "Application under review",
                    application.getReviewedDate());
        }

        if (application.getApprovedDate() != null) {
            addTimelineEvent(response, "Approved", "Application approved",
                    application.getApprovedDate());
        }

        if (application.getInvoiceSentDate() != null) {
            addTimelineEvent(response, "Invoice", "Invoice sent",
                    application.getInvoiceSentDate());
        }

        if (application.getPaymentReceivedDate() != null) {
            addTimelineEvent(response, "Payment", "Payment received",
                    application.getPaymentReceivedDate());
        }

        if (application.getEnrolledDate() != null) {
            addTimelineEvent(response, "Enrolled", "Student enrolled",
                    application.getEnrolledDate());
        }

        return response;
    }

    // ========== Helper Methods ==========

    private void addTimelineEvent(ApplicationTimelineResponse response,
                                  String stage,
                                  String description,
                                  LocalDateTime timestamp) {
        if (timestamp == null) return;

        ApplicationTimelineResponse.TimelineEvent event =
                new ApplicationTimelineResponse.TimelineEvent();
        event.setStage(stage);
        event.setDescription(description);
        event.setTimestamp(timestamp);
        event.setIsComplete(true);
        response.getEvents().add(event);
    }
}