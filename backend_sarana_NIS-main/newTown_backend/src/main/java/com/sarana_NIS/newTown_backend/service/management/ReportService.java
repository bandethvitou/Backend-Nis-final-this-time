package com.sarana_NIS.newTown_backend.service.management;

import com.sarana_NIS.newTown_backend.dto.request.management.ReportRequest;
import com.sarana_NIS.newTown_backend.dto.request.management.ReportReviewRequest;
import com.sarana_NIS.newTown_backend.dto.response.management.ReportStatsResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.management.Department;
import com.sarana_NIS.newTown_backend.model.entity.management.Report;
import com.sarana_NIS.newTown_backend.model.enums.ReportStatus;
import com.sarana_NIS.newTown_backend.repository.DepartmentRepository;
import com.sarana_NIS.newTown_backend.repository.ReportRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    private static final Logger log = LoggerFactory.getLogger(ReportService.class);

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    public ReportService(ReportRepository reportRepository,
                         UserRepository userRepository,
                         DepartmentRepository departmentRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

    // ============================================================
    // CREATE
    // ============================================================

    @Transactional
    public Report createReport(Report report, Long submittedByUserId) {
        User submittedBy = userRepository.findById(submittedByUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (report.getDepartment() != null && report.getDepartment().getId() != null) {
            Department department = departmentRepository.findById(report.getDepartment().getId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            report.setDepartment(department);
        }

        report.setSubmittedBy(submittedBy);
        report.setStatus(ReportStatus.DRAFT);
        report.setSubmittedDate(LocalDateTime.now());

        Report saved = reportRepository.save(report);
        log.info("Report created: {}", saved.getTitle());
        return saved;
    }

    @Transactional
    public Report createReportFromRequest(ReportRequest request, Long submittedByUserId) {
        User submittedBy = userRepository.findById(submittedByUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Report report = new Report();
        report.setType(request.getType());
        report.setTitle(request.getTitle());
        report.setContent(request.getContent());
        report.setSubmittedBy(submittedBy);
        report.setStatus(ReportStatus.DRAFT);
        report.setSubmittedDate(LocalDateTime.now());

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            report.setDepartment(department);
        }

        Report saved = reportRepository.save(report);
        log.info("Report created from request: {}", saved.getTitle());
        return saved;
    }

    // ============================================================
    // UPDATE
    // ============================================================

    @Transactional
    public Report updateReport(Long reportId, Report updatedReport) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        if (updatedReport.getTitle() != null) {
            report.setTitle(updatedReport.getTitle());
        }
        if (updatedReport.getContent() != null) {
            report.setContent(updatedReport.getContent());
        }
        if (updatedReport.getType() != null) {
            report.setType(updatedReport.getType());
        }
        if (updatedReport.getDepartment() != null) {
            Department department = departmentRepository.findById(updatedReport.getDepartment().getId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            report.setDepartment(department);
        }

        Report saved = reportRepository.save(report);
        log.info("Report updated: {}", saved.getTitle());
        return saved;
    }

    @Transactional
    public Report updateReportFromRequest(Long reportId, ReportRequest request) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        if (request.getTitle() != null) {
            report.setTitle(request.getTitle());
        }
        if (request.getContent() != null) {
            report.setContent(request.getContent());
        }
        if (request.getType() != null) {
            report.setType(request.getType());
        }

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            report.setDepartment(department);
        }

        Report saved = reportRepository.save(report);
        log.info("Report updated from request: {}", saved.getTitle());
        return saved;
    }

    // ============================================================
    // SUBMIT
    // ============================================================

    @Transactional
    public Report submitReport(Long reportId, Long submittedToUserId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        User submittedTo = userRepository.findById(submittedToUserId)
                .orElseThrow(() -> new RuntimeException("Reviewer not found"));

        report.submit(submittedTo);
        Report saved = reportRepository.save(report);
        log.info("Report submitted: {}", reportId);
        return saved;
    }

    // ============================================================
    // REVIEW
    // ============================================================

    @Transactional
    public Report reviewReport(ReportReviewRequest request, Long reviewerUserId) {
        Report report = reportRepository.findById(request.getReportId())
                .orElseThrow(() -> new RuntimeException("Report not found"));

        User reviewer = userRepository.findById(reviewerUserId)
                .orElseThrow(() -> new RuntimeException("Reviewer not found"));

        report.setReviewedBy(reviewer);
        report.setReviewedDate(LocalDateTime.now());

        switch (request.getAction()) {
            case "APPROVE":
                report.approve(reviewer);
                log.info("Report approved: {}", request.getReportId());
                break;
            case "REJECT":
                report.setStatus(ReportStatus.REJECTED);
                report.setFeedback(request.getRejectionReason());
                log.info("Report rejected: {}", request.getReportId());
                break;
            case "REQUEST_REVISION":
                report.requestRevision(request.getRevisionNotes());
                log.info("Report revision requested: {}", request.getReportId());
                break;
            default:
                throw new RuntimeException("Invalid action: " + request.getAction());
        }

        Report saved = reportRepository.save(report);
        return saved;
    }

    // ============================================================
    // RESUBMIT
    // ============================================================

    @Transactional
    public Report resubmitReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        report.setStatus(ReportStatus.SUBMITTED);
        report.setRevisionRequired(false);
        report.setSubmittedDate(LocalDateTime.now());

        Report saved = reportRepository.save(report);
        log.info("Report resubmitted: {}", reportId);
        return saved;
    }

    // ============================================================
    // READ / GET
    // ============================================================

    public Report getReportById(Long reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public List<Report> getReportsByStatus(ReportStatus status) {
        return reportRepository.findByStatus(status);
    }

    public List<Report> getPendingReview() {
        return reportRepository.findPendingReview();
    }

    public List<Report> getConfidentialReports() {
        return reportRepository.findConfidentialReports();
    }

    public List<Report> getDraftReportsByUser(Long userId) {
        return reportRepository.findDraftByUser(userId);
    }

    public List<Report> getReportsSubmittedToReviewer(Long userId) {
        return reportRepository.findSubmittedToReviewer(userId);
    }

    // ✅ FIXED: Use findByStatus instead of findUnapprovedSubmitted
    public List<Report> getUnapprovedSubmitted() {
        return reportRepository.findByStatus(ReportStatus.SUBMITTED);
    }

    // ============================================================
    // STATISTICS
    // ============================================================

    public ReportStatsResponse getReportStats() {
        ReportStatsResponse stats = new ReportStatsResponse();

        stats.setTotalReports((int) reportRepository.count());
        stats.setDraft(reportRepository.findByStatus(ReportStatus.DRAFT).size());
        stats.setSubmitted(reportRepository.findByStatus(ReportStatus.SUBMITTED).size());
        stats.setUnderReview(reportRepository.findByStatus(ReportStatus.UNDER_REVIEW).size());

        stats.setApproved(reportRepository.countApprovedReports().intValue());

        List<Report> rejectedReports = reportRepository.findByStatus(ReportStatus.REJECTED);
        stats.setRejected(rejectedReports.size());

        List<Report> revisionRequests = reportRepository.findRevisionRequired();
        stats.setRevisionRequests(revisionRequests.size());

        long totalProcessed = stats.getApproved() + stats.getRejected();
        stats.setApprovalRate(totalProcessed > 0 ? (double) stats.getApproved() / totalProcessed * 100 : 0);

        return stats;
    }

    // ============================================================
    // VIEW COUNT
    // ============================================================

    @Transactional
    public void incrementViewCount(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        report.incrementViewCount();
        reportRepository.save(report);
    }

    // ============================================================
    // DELETE
    // ============================================================

    @Transactional
    public void deleteReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        if (report.getStatus() == ReportStatus.APPROVED) {
            throw new RuntimeException("Cannot delete an approved report");
        }

        reportRepository.delete(report);
        log.info("Report deleted: {}", reportId);
    }
}