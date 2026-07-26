package com.sarana_NIS.newTown_backend.controller.management;

import com.sarana_NIS.newTown_backend.dto.request.management.ReportRequest;
import com.sarana_NIS.newTown_backend.dto.request.management.ReportReviewRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.management.ReportResponse;
import com.sarana_NIS.newTown_backend.dto.response.management.ReportStatsResponse;
import com.sarana_NIS.newTown_backend.model.entity.management.Report;
import com.sarana_NIS.newTown_backend.model.enums.ReportStatus;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.management.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Report Management", description = "Report management APIs")
public class ReportController {

    private static final Logger log = LoggerFactory.getLogger(ReportController.class);

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get all reports")
    public ResponseEntity<ApiResponse<List<Report>>> getAllReports() {
        log.info("Fetching all reports");
        List<Report> reports = reportService.getAllReports();
        return ResponseEntity.ok(ApiResponse.success(reports));
    }

    @GetMapping("/{id}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get report by ID")
    public ResponseEntity<ApiResponse<Report>> getReportById(@PathVariable Long id) {
        log.info("Fetching report: {}", id);
        Report report = reportService.getReportById(id);
        return ResponseEntity.ok(ApiResponse.success(report));
    }

    @GetMapping("/status/{status}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get reports by status")
    public ResponseEntity<ApiResponse<List<Report>>> getReportsByStatus(@PathVariable ReportStatus status) {
        log.info("Fetching reports by status: {}", status);
        List<Report> reports = reportService.getReportsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(reports));
    }

    @GetMapping("/pending-review")
    @RequiresRole({"TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get reports pending review")
    public ResponseEntity<ApiResponse<List<Report>>> getPendingReview() {
        log.info("Fetching reports pending review");
        List<Report> reports = reportService.getPendingReview();
        return ResponseEntity.ok(ApiResponse.success(reports));
    }

    @GetMapping("/stats")
    @RequiresRole({"TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get report statistics")
    public ResponseEntity<ApiResponse<ReportStatsResponse>> getReportStats() {
        log.info("Fetching report statistics");
        ReportStatsResponse response = reportService.getReportStats();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("REPORT_CREATE")
    @Operation(summary = "Create a new report")
    public ResponseEntity<ApiResponse<Report>> createReport(
            @Valid @RequestBody ReportRequest request) {
        log.info("Creating report: {}", request.getTitle());
        Long currentUserId = 1L; // TODO: Get from SecurityContext
        // ✅ Fix: Convert ReportRequest to Report entity first
        Report report = reportService.createReportFromRequest(request, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Report created successfully", report));
    }

    @PostMapping("/{id}/submit")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("REPORT_SUBMIT")
    @Operation(summary = "Submit report for review")
    public ResponseEntity<ApiResponse<Report>> submitReport(
            @PathVariable Long id,
            @RequestParam Long submittedToUserId) {
        log.info("Submitting report: {}", id);
        Report report = reportService.submitReport(id, submittedToUserId);
        return ResponseEntity.ok(ApiResponse.success("Report submitted successfully", report));
    }

    @PostMapping("/review")
    @RequiresRole({"TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("REPORT_REVIEW")
    @Operation(summary = "Review report")
    public ResponseEntity<ApiResponse<Report>> reviewReport(
            @Valid @RequestBody ReportReviewRequest request) {
        log.info("Reviewing report: {}", request.getReportId());
        Long currentUserId = 1L; // TODO: Get from SecurityContext
        Report report = reportService.reviewReport(request, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Report reviewed successfully", report));
    }

    @PutMapping("/{id}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("REPORT_UPDATE")
    @Operation(summary = "Update report")
    public ResponseEntity<ApiResponse<Report>> updateReport(
            @PathVariable Long id,
            @Valid @RequestBody ReportRequest request) {
        log.info("Updating report: {}", id);
        // ✅ Fix: Use service method to update report from request
        Report report = reportService.updateReportFromRequest(id, request);
        return ResponseEntity.ok(ApiResponse.success("Report updated successfully", report));
    }

    @DeleteMapping("/{id}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("REPORT_DELETE")
    @Operation(summary = "Delete report")
    public ResponseEntity<ApiResponse<Void>> deleteReport(@PathVariable Long id) {
        log.info("Deleting report: {}", id);
        reportService.deleteReport(id);
        return ResponseEntity.ok(ApiResponse.success("Report deleted successfully", null));
    }
}