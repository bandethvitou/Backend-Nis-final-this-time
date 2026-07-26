package com.sarana_NIS.newTown_backend.controller.dashboard;

import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.dashboard.TopManagerDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/top-manager/dashboard")
@Tag(name = "Top Manager Dashboard", description = "Top Manager dashboard APIs")
public class TopManagerDashboardController {

    private final TopManagerDashboardService dashboardService;

    public TopManagerDashboardController(TopManagerDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    @RequiresRole("TOP_MANAGER")
    @Operation(summary = "Get top manager dashboard statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboard() {
        System.out.println("Fetching top manager dashboard");
        Map<String, Object> stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/enrollment")
    @RequiresRole("TOP_MANAGER")
    @Operation(summary = "Get enrollment summary")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getEnrollmentSummary() {
        System.out.println("Fetching enrollment summary");
        Map<String, Object> summary = dashboardService.getEnrollmentSummary();
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @GetMapping("/score-completion")
    @RequiresRole("TOP_MANAGER")
    @Operation(summary = "Get score completion status")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getScoreCompletion() {
        System.out.println("Fetching score completion status");
        Map<String, Object> status = dashboardService.getScoreCompletion();
        return ResponseEntity.ok(ApiResponse.success(status));
    }

    @GetMapping("/pending-reviews")
    @RequiresRole("TOP_MANAGER")
    @Operation(summary = "Get pending reviews")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getPendingReviews() {
        System.out.println("Fetching pending reviews");
        Map<String, Object> reviews = dashboardService.getPendingReviews();
        return ResponseEntity.ok(ApiResponse.success(reviews));
    }
}