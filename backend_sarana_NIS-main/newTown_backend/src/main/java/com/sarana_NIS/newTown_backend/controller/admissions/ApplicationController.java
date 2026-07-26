package com.sarana_NIS.newTown_backend.controller.admissions;

import com.sarana_NIS.newTown_backend.dto.request.admissions.ApplicationFilterRequest;
import com.sarana_NIS.newTown_backend.dto.request.admissions.ApplicationReviewRequest;
import com.sarana_NIS.newTown_backend.dto.request.admissions.ApplicationStatusRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.dto.response.admissions.ApplicationStatsResponse;
import com.sarana_NIS.newTown_backend.dto.response.admissions.ApplicationTimelineResponse;
import com.sarana_NIS.newTown_backend.model.entity.admissions.Application;
import com.sarana_NIS.newTown_backend.model.enums.ApplicationStatus;
import com.sarana_NIS.newTown_backend.model.enums.Level;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.admissions.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admissions/applications")
@Tag(name = "Application Management", description = "Student application management APIs")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    @RequiresRole({"ADMISSIONS_OFFICER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("APPLICATION_CREATE")
    @Operation(summary = "Create a new application")
    public ResponseEntity<ApiResponse<Application>> createApplication(
            @Valid @RequestBody Application application) {
        System.out.println("Creating new application for student: " + application.getStudentName());
        Application created = applicationService.createApplication(application);
        return ResponseEntity.ok(ApiResponse.success("Application created successfully", created));
    }

    @PostMapping("/{applicationId}/submit")
    @RequiresRole({"ADMISSIONS_OFFICER", "RECEPTIONIST", "PARENT"})
    @RequiresPermission("APPLICATION_SUBMIT")
    @Operation(summary = "Submit application for review")
    public ResponseEntity<ApiResponse<Application>> submitApplication(
            @PathVariable Long applicationId) {
        System.out.println("Submitting application: " + applicationId);
        Application application = applicationService.submitApplication(applicationId);
        return ResponseEntity.ok(ApiResponse.success("Application submitted successfully", application));
    }

    @GetMapping("/{applicationId}")
    @RequiresRole({"ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get application by ID")
    public ResponseEntity<ApiResponse<Application>> getApplicationById(
            @PathVariable Long applicationId) {
        System.out.println("Fetching application: " + applicationId);
        Application application = applicationService.getApplicationById(applicationId);
        return ResponseEntity.ok(ApiResponse.success(application));
    }

    @GetMapping("/status/{status}")
    @RequiresRole({"ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get applications by status")
    public ResponseEntity<ApiResponse<List<Application>>> getApplicationsByStatus(
            @PathVariable ApplicationStatus status) {
        System.out.println("Fetching applications by status: " + status);
        List<Application> applications = applicationService.getApplicationsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(applications));
    }

    @GetMapping("/level/{level}")
    @RequiresRole({"ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get applications by level")
    public ResponseEntity<ApiResponse<List<Application>>> getApplicationsByLevel(
            @PathVariable Level level) {
        System.out.println("Fetching applications by level: " + level);
        List<Application> applications = applicationService.getApplicationsByLevel(level);
        return ResponseEntity.ok(ApiResponse.success(applications));
    }

    @GetMapping("/pending-review")
    @RequiresRole({"ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get applications pending review")
    public ResponseEntity<ApiResponse<List<Application>>> getPendingReviews() {
        System.out.println("Fetching applications pending review");
        List<Application> applications = applicationService.getPendingReviews();
        return ResponseEntity.ok(ApiResponse.success(applications));
    }

    @GetMapping("/approved-not-enrolled")
    @RequiresRole({"ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get approved applications not yet enrolled")
    public ResponseEntity<ApiResponse<List<Application>>> getApprovedNotEnrolled() {
        System.out.println("Fetching approved applications not yet enrolled");
        List<Application> applications = applicationService.getApprovedNotEnrolled();
        return ResponseEntity.ok(ApiResponse.success(applications));
    }

    @PostMapping("/search")
    @RequiresRole({"ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Search applications")
    public ResponseEntity<ApiResponse<PageResponse<Application>>> searchApplications(
            @Valid @RequestBody ApplicationFilterRequest request) {
        System.out.println("Searching applications with keyword: " + request.getKeyword());
        PageResponse<Application> response = applicationService.searchApplications(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/review")
    @RequiresRole({"ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("APPLICATION_REVIEW")
    @Operation(summary = "Review application")
    public ResponseEntity<ApiResponse<Application>> reviewApplication(
            @Valid @RequestBody ApplicationReviewRequest request) {
        System.out.println("Reviewing application: " + request.getApplicationId());
        Long currentUserId = 1L;
        Application application = applicationService.reviewApplication(request, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Application reviewed successfully", application));
    }

    @PutMapping("/status")
    @RequiresRole({"ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("APPLICATION_UPDATE")
    @Operation(summary = "Update application status")
    public ResponseEntity<ApiResponse<Application>> updateStatus(
            @Valid @RequestBody ApplicationStatusRequest request) {
        System.out.println("Updating application status: " + request.getApplicationId());
        Application application = applicationService.updateStatus(request);
        return ResponseEntity.ok(ApiResponse.success("Application status updated successfully", application));
    }

    @GetMapping("/{applicationId}/timeline")
    @RequiresRole({"ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR", "PARENT"})
    @Operation(summary = "Get application timeline")
    public ResponseEntity<ApiResponse<ApplicationTimelineResponse>> getApplicationTimeline(
            @PathVariable Long applicationId) {
        System.out.println("Fetching application timeline: " + applicationId);
        ApplicationTimelineResponse response = applicationService.getApplicationTimeline(applicationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/stats")
    @RequiresRole({"ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get application statistics")
    public ResponseEntity<ApiResponse<ApplicationStatsResponse>> getApplicationStats() {
        System.out.println("Fetching application statistics");
        ApplicationStatsResponse stats = applicationService.getApplicationStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @DeleteMapping("/{applicationId}")
    @RequiresRole({"ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("APPLICATION_DELETE")
    @Operation(summary = "Delete application")
    public ResponseEntity<ApiResponse<Void>> deleteApplication(
            @PathVariable Long applicationId) {
        System.out.println("Deleting application: " + applicationId);
        applicationService.deleteApplication(applicationId);
        return ResponseEntity.ok(ApiResponse.success("Application deleted successfully", null));
    }
}