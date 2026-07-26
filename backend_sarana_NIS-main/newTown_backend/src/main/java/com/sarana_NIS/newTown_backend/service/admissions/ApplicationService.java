package com.sarana_NIS.newTown_backend.service.admissions;

import com.sarana_NIS.newTown_backend.dto.request.admissions.ApplicationFilterRequest;
import com.sarana_NIS.newTown_backend.dto.request.admissions.ApplicationReviewRequest;
import com.sarana_NIS.newTown_backend.dto.request.admissions.ApplicationStatusRequest;
import com.sarana_NIS.newTown_backend.dto.response.admissions.ApplicationStatsResponse;
import com.sarana_NIS.newTown_backend.dto.response.admissions.ApplicationTimelineResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.model.entity.admissions.Application;
import com.sarana_NIS.newTown_backend.model.entity.academic.Class;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.enums.ApplicationStatus;
import com.sarana_NIS.newTown_backend.model.enums.Level;
import com.sarana_NIS.newTown_backend.repository.ApplicationRepository;
import com.sarana_NIS.newTown_backend.repository.ClassRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApplicationService {

    private static final Logger log = LoggerFactory.getLogger(ApplicationService.class);

    private final ApplicationRepository applicationRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;

    private static final List<String> APPLICATION_FLOW = List.of(
            "INQUIRY", "APPLICATION", "REVIEW", "CAMPUS_TOUR",
            "ASSESSMENT", "APPROVAL", "INVOICE", "ENROLLMENT"
    );

    public ApplicationService(ApplicationRepository applicationRepository, ClassRepository classRepository,
                              UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.classRepository = classRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Application createApplication(Application application) {
        application.setStatus(ApplicationStatus.DRAFT);
        application.setInquiryDate(LocalDateTime.now());
        Application saved = applicationRepository.save(application);
        log.info("Application created for student: {}", application.getStudentName());
        return saved;
    }

    @Transactional
    public Application submitApplication(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (application.getStatus() != ApplicationStatus.DRAFT &&
                application.getStatus() != ApplicationStatus.SUBMITTED) {
            throw new RuntimeException("Application cannot be submitted in current status: " + application.getStatus());
        }

        application.setStatus(ApplicationStatus.SUBMITTED);
        application.setSubmittedDate(LocalDateTime.now());
        Application saved = applicationRepository.save(application);
        log.info("Application submitted: {}", applicationId);
        return saved;
    }

    @Transactional
    public Application reviewApplication(ApplicationReviewRequest request, Long reviewerUserId) {
        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found"));
        User reviewer = userRepository.findById(reviewerUserId)
                .orElseThrow(() -> new RuntimeException("Reviewer not found"));

        switch (request.getAction()) {
            case "APPROVE":
                application.setStatus(ApplicationStatus.APPROVED);
                application.setApprovedDate(LocalDateTime.now());
                break;
            case "REJECT":
                application.setStatus(ApplicationStatus.REJECTED);
                application.setRejectionReason(request.getRejectionReason());
                break;
            case "REQUEST_DOCUMENTS":
                application.setStatus(ApplicationStatus.DOCUMENTS_REQUESTED);
                application.setDocumentsRequested(request.getDocumentsRequested());
                break;
            default:
                throw new RuntimeException("Invalid action: " + request.getAction());
        }

        application.setReviewedBy(reviewer);
        application.setReviewedDate(LocalDateTime.now());
        application.setReviewNotes(request.getReviewNotes());

        if (request.getAction().equals("APPROVE") && request.getAssignedClassId() != null) {
            Class targetClass = classRepository.findById(request.getAssignedClassId())
                    .orElseThrow(() -> new RuntimeException("Class not found"));
            application.setTargetClass(targetClass);
        }

        Application saved = applicationRepository.save(application);
        log.info("Application {}: {}", request.getAction(), application.getId());
        return saved;
    }

    @Transactional
    public Application updateStatus(ApplicationStatusRequest request) {
        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found"));

        ApplicationStatus newStatus = ApplicationStatus.valueOf(request.getStatus());
        validateStatusTransition(application.getStatus(), newStatus);
        application.setStatus(newStatus);

        switch (newStatus) {
            case SUBMITTED:
                application.setSubmittedDate(LocalDateTime.now());
                break;
            case UNDER_REVIEW:
                application.setReviewedDate(LocalDateTime.now());
                break;
            case APPROVED:
                application.setApprovedDate(LocalDateTime.now());
                break;
            case INVOICE_SENT:
                application.setInvoiceSentDate(LocalDateTime.now());
                break;
            case PAID:
                application.setPaymentReceivedDate(LocalDateTime.now());
                break;
            case ENROLLED:
                application.setEnrolledDate(LocalDateTime.now());
                break;
            default:
                break;
        }

        Application saved = applicationRepository.save(application);
        log.info("Application status updated: {} -> {}", application.getId(), newStatus);
        return saved;
    }

    public Application getApplicationById(Long applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }

    public List<Application> getApplicationsByStatus(ApplicationStatus status) {
        return applicationRepository.findByStatus(status);
    }

    public List<Application> getApplicationsByLevel(Level level) {
        return applicationRepository.findByLevel(level);
    }

    public List<Application> getPendingReviews() {
        return applicationRepository.findPendingReview();
    }

    public List<Application> getApprovedNotEnrolled() {
        return applicationRepository.findApprovedNotEnrolled();
    }

    public PageResponse<Application> searchApplications(ApplicationFilterRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize(),
                Sort.by(request.getSortDirection().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, request.getSortBy()));

        Page<Application> page = applicationRepository.searchApplications(
                request.getKeyword() != null ? request.getKeyword() : "", pageRequest);

        return new PageResponse<>(page.getContent(), page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages());
    }

    public ApplicationTimelineResponse getApplicationTimeline(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        ApplicationTimelineResponse response = new ApplicationTimelineResponse();
        response.setApplicationId(application.getId());
        response.setStudentName(application.getStudentName());
        response.setParentName(application.getParentName());
        response.setStatus(application.getStatus().name());

        List<ApplicationTimelineResponse.TimelineEvent> events = new ArrayList<>();
        addTimelineEvent(events, "INQUIRY", "Initial Inquiry", application.getInquiryDate());
        addTimelineEvent(events, "SUBMITTED", "Application Submitted", application.getSubmittedDate());
        addTimelineEvent(events, "REVIEWED", "Application Reviewed", application.getReviewedDate());
        addTimelineEvent(events, "APPROVED", "Application Approved", application.getApprovedDate());
        addTimelineEvent(events, "INVOICE_SENT", "Invoice Sent", application.getInvoiceSentDate());
        addTimelineEvent(events, "PAYMENT_RECEIVED", "Payment Received", application.getPaymentReceivedDate());
        addTimelineEvent(events, "ENROLLED", "Student Enrolled", application.getEnrolledDate());

        response.setEvents(events);
        response.setIsCompleted(application.getStatus() == ApplicationStatus.ENROLLED);
        return response;
    }

    private void addTimelineEvent(List<ApplicationTimelineResponse.TimelineEvent> events, String stage,
                                  String description, LocalDateTime timestamp) {
        if (timestamp != null) {
            ApplicationTimelineResponse.TimelineEvent event = new ApplicationTimelineResponse.TimelineEvent();
            event.setStage(stage);
            event.setDescription(description);
            event.setTimestamp(timestamp);
            event.setIsComplete(true);
            events.add(event);
        }
    }

    public ApplicationStatsResponse getApplicationStats() {
        ApplicationStatsResponse stats = new ApplicationStatsResponse();

        long total = applicationRepository.count();
        stats.setTotalApplications((int) total);
        stats.setPendingApplications(applicationRepository.countByStatus(ApplicationStatus.SUBMITTED).intValue());
        stats.setApprovedApplications(applicationRepository.countByStatus(ApplicationStatus.APPROVED).intValue());
        stats.setRejectedApplications(applicationRepository.countByStatus(ApplicationStatus.REJECTED).intValue());
        stats.setEnrolledApplications(applicationRepository.countByStatus(ApplicationStatus.ENROLLED).intValue());

        Map<String, Integer> statusBreakdown = new HashMap<>();
        for (ApplicationStatus status : ApplicationStatus.values()) {
            Long count = applicationRepository.countByStatus(status);
            if (count > 0) {
                statusBreakdown.put(status.name(), count.intValue());
            }
        }
        stats.setStatusBreakdown(statusBreakdown);

        Map<String, Integer> levelBreakdown = new HashMap<>();
        List<Object[]> levelResults = applicationRepository.findEnrolledByLevel();
        for (Object[] result : levelResults) {
            levelBreakdown.put(result[0].toString(), ((Number) result[1]).intValue());
        }
        stats.setLevelBreakdown(levelBreakdown);

        double enrolled = stats.getEnrolledApplications();
        double totalApps = stats.getTotalApplications();
        stats.setConversionRate(totalApps > 0 ? (enrolled / totalApps) * 100 : 0);

        double approved = stats.getApprovedApplications();
        double submitted = stats.getPendingApplications() + approved + stats.getRejectedApplications();
        stats.setApprovalRate(submitted > 0 ? (approved / submitted) * 100 : 0);

        return stats;
    }

    @Transactional
    public void deleteApplication(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        applicationRepository.delete(application);
        log.info("Application deleted: {}", applicationId);
    }

    private void validateStatusTransition(ApplicationStatus current, ApplicationStatus next) {
        int currentIndex = APPLICATION_FLOW.indexOf(current.name());
        int nextIndex = APPLICATION_FLOW.indexOf(next.name());

        if (currentIndex == -1 || nextIndex == -1) {
            throw new RuntimeException("Invalid application status");
        }

        if (Math.abs(nextIndex - currentIndex) > 1 && next != ApplicationStatus.REJECTED) {
            throw new RuntimeException("Invalid status transition from " + current + " to " + next);
        }
    }
}