package com.sarana_NIS.newTown_backend.controller.dashboard;

import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.dashboard.ManagerDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/manager/dashboard")
@Tag(name = "Manager Dashboard", description = "Manager dashboard APIs")
public class ManagerDashboardController {

    private static final Logger log = LoggerFactory.getLogger(ManagerDashboardController.class);

    private final ManagerDashboardService dashboardService;

    public ManagerDashboardController(ManagerDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    @RequiresRole("MANAGER")
    @Operation(summary = "Get manager dashboard statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboard() {
        log.info("Fetching manager dashboard");
        Map<String, Object> stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/team")
    @RequiresRole("MANAGER")
    @Operation(summary = "Get team statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTeamStats() {
        log.info("Fetching team statistics");
        Map<String, Object> stats = dashboardService.getTeamStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/pending")
    @RequiresRole("MANAGER")
    @Operation(summary = "Get pending items")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getPendingItems() {
        log.info("Fetching pending items");
        Map<String, Object> items = dashboardService.getPendingItems();
        return ResponseEntity.ok(ApiResponse.success(items));
    }
}