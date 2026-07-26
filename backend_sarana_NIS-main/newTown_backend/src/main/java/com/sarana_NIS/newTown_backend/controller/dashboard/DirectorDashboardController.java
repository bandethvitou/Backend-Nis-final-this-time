package com.sarana_NIS.newTown_backend.controller.dashboard;

import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.core.DashboardStatsResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.dashboard.DirectorDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/director/dashboard")
@Tag(name = "Director Dashboard", description = "Director dashboard APIs")
public class DirectorDashboardController {

    private static final Logger log = LoggerFactory.getLogger(DirectorDashboardController.class);

    private final DirectorDashboardService dashboardService;

    public DirectorDashboardController(DirectorDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    @RequiresRole("DIRECTOR")
    @Operation(summary = "Get director dashboard statistics")
    public ResponseEntity<ApiResponse<DashboardStatsResponse>> getDashboard() {
        log.info("Fetching director dashboard");
        DashboardStatsResponse stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/enrollment")
    @RequiresRole("DIRECTOR")
    @Operation(summary = "Get enrollment statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getEnrollmentStats() {
        log.info("Fetching enrollment statistics");
        Map<String, Object> stats = dashboardService.getEnrollmentStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/finance")
    @RequiresRole("DIRECTOR")
    @Operation(summary = "Get finance overview")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getFinanceOverview() {
        log.info("Fetching finance overview");
        Map<String, Object> stats = dashboardService.getFinanceOverview();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/alerts")
    @RequiresRole("DIRECTOR")
    @Operation(summary = "Get director alerts")
    public ResponseEntity<ApiResponse<List<DashboardStatsResponse.Alert>>> getAlerts() {
        log.info("Fetching director alerts");
        List<DashboardStatsResponse.Alert> alerts = dashboardService.getAlerts();
        return ResponseEntity.ok(ApiResponse.success(alerts));
    }
}